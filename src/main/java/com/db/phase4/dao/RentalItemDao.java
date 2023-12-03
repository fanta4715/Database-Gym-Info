package com.db.phase4.dao;

import com.db.phase4.dto.RentalItemViewDto;
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
public class RentalItemDao {
    private final ConnectionMaker connectionMaker;

    public List<RentalItemViewDto> findByGymId(int gymId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<RentalItemViewDto> rentalItems = new ArrayList<>();
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

//            String itemName;
//            int gymId;
//            int rentalFee;
//            int maxQuantity;

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT item_name, gym_id, rental_fee, max_quantity  ");
            sb.append("FROM rental_item ");
            sb.append("WHERE gym_id ="+gymId);

            rs = stmt.executeQuery(sb.toString());

            while(rs.next()){
                String itemName = rs.getString(1);
//                int gymId = rs.getInt(2); 받을 필요 없음
                int rentalFee = rs.getInt(3);
                int maxQuantity = rs.getInt(4);
                rentalItems.add(RentalItemViewDto.builder()
                                .itemName(itemName)
                                .gymId(gymId)
                                .rentalFee(rentalFee)
                                .maxQuantity(maxQuantity)
                                .build()
                        );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return rentalItems;
        }
    }
}
