package com.db.phase4.dao;

import com.db.phase4.dto.MachineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MachineDao {
    @Value("${TEST_ID}")
    String ID;
    @Value("${TEST_PW}")
    String PW;
    @Value("${TEST_URL}")
    String URL;

    public List<MachineDto> getWithGymId(String gymId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, ID, PW);

            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            String sql = "SELECT * FROM MACHINE M JOIN GYM G ON G.Gym_id = M.Gym_id WHERE G.Gym_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gymId);
            ResultSet rs = pstmt.executeQuery();

            List<MachineDto> machineDtos = MachineDto.of(rs);

            conn.commit();

            return machineDtos;
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }

    public void requestMachine(String gymId, String machineId, String userId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, ID, PW);

            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            // 유저가 해당 gym에 등록되어있는지 확인
            String sql = "SELECT * FROM GYM G JOIN MACHINE M ON G.Gym_id = M.Gym_id JOIN ENROLLS E ON E.Gym_id = G.Gym_id WHERE G.Gym_id = ? AND M.Machine_id = ? AND E.User_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gymId);
            pstmt.setString(2, machineId);
            pstmt.setString(3, userId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new SQLException("user Gym와 machine Gym이 일치하지 않습니다.");
            }
            // TODO : unusable이고 using user가 없고 본인이 reserve한 machine이어야 사용가능

//            String sql1 = "UPDATE USERS SET USING_MACHINE_ID = ? WHERE USER_ID = ?";
//            String sql2 = "UPDATE MACHINE SET STATE = 'reservable' WHERE MACHINE_ID = ?";
//            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
//            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
//            pstmt1.setString(1, machineId);
//            pstmt1.setString(2, userId);
//            pstmt2.setString(1, machineId);
//            pstmt1.executeUpdate();
//            pstmt2.executeUpdate();

            conn.commit();
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void reserveMachine(String gymId, String machineId, String userId) {
    }
}
