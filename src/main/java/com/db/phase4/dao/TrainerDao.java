package com.db.phase4.dao;

import com.db.phase4.dto.review.ReviewViewDto;
import com.db.phase4.dto.trainer.TrainerViewDto;
import com.db.phase4.util.ConnectionMaker;

import java.sql.Connection;
import java.sql.DriverManager;
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
public class TrainerDao {
    private final ConnectionMaker connectionMaker;

    public List<TrainerViewDto> findByGymId(int gymId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<TrainerViewDto> trainers = new ArrayList<>();
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT name, trainer_id, specialization, work_year, birth_date, sex ");
            sb.append("FROM trainer ");
            sb.append("WHERE gym_id =" + gymId);

            rs = stmt.executeQuery(sb.toString());

            while (rs.next()) {
                String name = rs.getString(1);
                int trainerId = rs.getInt(2);
                String specialization = rs.getString(3);
                int workYear = rs.getInt(4);
                LocalDate birthDate = rs.getDate(5).toLocalDate();
                String sex = rs.getString(6);
                trainers.add(TrainerViewDto.builder()
                        .name(name)
                        .trainerId(trainerId)
                        .specialization(specialization)
                        .workYear(workYear)
                        .birthDate(birthDate)
                        .sex(sex)
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return trainers;
        }
    }
}
