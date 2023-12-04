package com.db.phase4.dao;

import com.db.phase4.dto.RentalDto;
import com.db.phase4.dto.RentalViewDto;
import com.db.phase4.util.ConnectionMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RentalDao {
    private final ConnectionMaker connectionMaker;

    public List<RentalViewDto> getWtihGymId(int gymId, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.createConnection();

            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            String sql = "SELECT * FROM GYM G JOIN RENTAL_ITEM R ON G.Gym_id = R.Gym_id WHERE G.Gym_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gymId);
            rs = pstmt.executeQuery();

            List<RentalDto> rentalDtos = RentalDto.of(rs);
            List<RentalViewDto> rentalViewDtos = new ArrayList<>();

            // 현재 갯수 및 사용자가 해당 물품을 대여했는지 여부 조회
            for (RentalDto rentalDto : rentalDtos) {
                sql = "SELECT COUNT(*) FROM RENTS WHERE Gym_id = ? AND Item_name = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, gymId);
                pstmt.setString(2, rentalDto.getItemName());
                rs = pstmt.executeQuery();
                rs.next();
                //현재 수량은 maxQuantity - 대여한 수량
                int currentQuantity = rentalDto.getMaxQuantity() - rs.getInt(1);

                sql = "SELECT COUNT(*) FROM RENTS WHERE Gym_id = ? AND Item_name = ? AND User_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, gymId);
                pstmt.setString(2, rentalDto.getItemName());
                pstmt.setInt(3, userId);
                rs = pstmt.executeQuery();
                rs.next();
                boolean isRented = rs.getInt(1) > 0;

                rentalViewDtos.add(new RentalViewDto(rentalDto, currentQuantity, isRented));
            }

            conn.commit();

            return rentalViewDtos;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            connectionMaker.closeAllWthPstmt(conn, pstmt, rs);
        }
        return new ArrayList<>();
    }

    public void requestRental(int userId, int gymId, String itemName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.createConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            String sql = "INSERT INTO RENTS(User_id, Gym_id, Item_name) VALUES(?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, gymId);
            pstmt.setString(3, itemName);
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            connectionMaker.closeAllWthPstmt(conn, pstmt, rs);
        }
    }
}
