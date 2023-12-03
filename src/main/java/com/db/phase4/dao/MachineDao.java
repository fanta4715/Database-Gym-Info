package com.db.phase4.dao;

import com.db.phase4.dto.MachineViewDto;
import com.db.phase4.dto.RentalItemViewDto;
import com.db.phase4.util.ConnectionMaker;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MachineDao {
    private final ConnectionMaker connectionMaker;

    public List<MachineViewDto> findByGymId(int gymId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<MachineViewDto> machines = new ArrayList<>();
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

//            int machineId;
//            String name;
//            String type;
//            String targetMuscle;
//            String state;

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT machine_id, name, type, target_muscle, state  ");
            sb.append("FROM machine ");
            sb.append("WHERE gym_id =" + gymId);

            rs = stmt.executeQuery(sb.toString());

            while (rs.next()) {
                int machineId = rs.getInt(1);
                String name = rs.getString(2);
                String type = rs.getString(3);
                String targetMuscle = rs.getString(4);
                String state = rs.getString(5);

                machines.add(MachineViewDto.builder()
                        .machineId(machineId)
                        .name(name)
                        .type(type).
                        targetMuscle(targetMuscle).
                        state(state).
                        build()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return machines;
        }
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
