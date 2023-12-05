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

            String sql = "SELECT * FROM GYM G JOIN RENTAL_ITEM R ON G.Gym_id = R.Gym_id WHERE G.Gym_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gymId);
            rs = pstmt.executeQuery();

            List<RentalDto> rentalDtos = RentalDto.of(rs);
            List<RentalViewDto> rentalViewDtos = new ArrayList<>();

            // 현재 갯수 및 사용자가 해당 물품을 대여했는지 여부 조회
            for (RentalDto rentalDto : rentalDtos) {
                PreparedStatement tempPstmt1 = conn.prepareStatement("SELECT COUNT(*) FROM RENTS WHERE Gym_id = ? AND Item_name = ?");
                tempPstmt1.setInt(1, gymId);
                tempPstmt1.setString(2, rentalDto.getItemName());
                ResultSet tempRs1 = tempPstmt1.executeQuery();
                tempRs1.next();
                int currentQuantity = rentalDto.getMaxQuantity() - tempRs1.getInt(1);

                PreparedStatement tempPstmt2 = conn.prepareStatement("SELECT COUNT(*) FROM RENTS WHERE Gym_id = ? AND Item_name = ? AND User_id = ?");
                tempPstmt2.setInt(1, gymId);
                tempPstmt2.setString(2, rentalDto.getItemName());
                tempPstmt2.setInt(3, userId);
                ResultSet tempRs2 = tempPstmt2.executeQuery();
                tempRs2.next();
                boolean isRented = tempRs2.getInt(1) > 0;

                rentalViewDtos.add(new RentalViewDto(rentalDto, currentQuantity, isRented));

                tempPstmt1.close();
                tempPstmt2.close();
                tempRs1.close();
                tempRs2.close();
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

            pstmt = conn.prepareStatement("INSERT INTO RENTS(User_id, Gym_id, Item_name) VALUES(?, ?, ?)");
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

    public void returnRental(int userId, int gymId, String itemName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.createConnection();

            pstmt = conn.prepareStatement("DELETE FROM RENTS WHERE User_id = ? AND Gym_id = ? AND Item_name = ?");
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
