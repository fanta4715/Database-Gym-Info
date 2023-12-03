package com.db.phase4.dao;

import com.db.phase4.dto.GymViewDto;
import com.db.phase4.dto.review.ReviewViewDto;
import com.db.phase4.util.ConnectionMaker;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GymDao {
    private final ConnectionMaker connectionMaker;

    public List<GymViewDto> findAll(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<GymViewDto> gyms = new ArrayList<>();
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

//            String name;
//            String location;
//            String contact;
//            int capacity;
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT g.gym_id, g.name, g.location, g.contact, g.capacity ");
            sb.append("FROM GYM g, ENROLLS e ");
            sb.append("WHERE g.gym_id = e.gym_id AND e.user_id =" + userId);

            rs = stmt.executeQuery(sb.toString());

            while (rs.next()) {
                int gymId = rs.getInt(1);
                String name = rs.getString(2);
                String location = rs.getString(3);
                String contact = rs.getString(4);
                int capacity = rs.getInt(5);
                gyms.add(GymViewDto.builder()
                        .gymId(gymId)
                        .name(name)
                        .contact(contact)
                        .location(location)
                        .capacity(capacity)
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return gyms;
        }
    }
}
