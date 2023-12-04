package com.db.phase4.dao;

import com.db.phase4.dto.MachineDto;
import com.db.phase4.dto.MachineViewDto;
import com.db.phase4.util.ConnectionMaker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MachineDao {
    private final ConnectionMaker connectionMaker;

    public List<MachineViewDto> getWithGymId(int gymId, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.createConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            String sql = "SELECT * FROM MACHINE M JOIN GYM G ON G.Gym_id = M.Gym_id WHERE G.Gym_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gymId);
            rs = pstmt.executeQuery();

            List<MachineDto> machineDtos = MachineDto.of(rs);
            List<MachineViewDto> machineViewDtos = new ArrayList<>();
            for (MachineDto machineDto : machineDtos) {
                // 해당 기구를 사용하는 User가 있는지 확인 + 있다면 그 사람이 본인인지 확인
                PreparedStatement tempPstmt1 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE USING_MACHINE_ID = ?");
                tempPstmt1.setInt(1, machineDto.getMachineId());
                ResultSet tempRs1 = tempPstmt1.executeQuery();
                tempRs1.next();
                boolean isUsing = tempRs1.getInt(1) > 0;

                PreparedStatement tempPstmt2 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE USING_MACHINE_ID = ? AND USER_ID = ?");
                tempPstmt2.setInt(1, machineDto.getMachineId());
                tempPstmt2.setInt(2, userId);
                ResultSet tempRs2 = tempPstmt2.executeQuery();
                tempRs2.next();
                boolean isDoing = tempRs2.getInt(1) > 0;

                // 해당 기구를 예약하는 User가 있는지 확인 + 있다면 그 사람이 본인인지 확인
                PreparedStatement tempPstmt3 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE RESERVE_MACHINE_ID = ?");
                tempPstmt3.setInt(1, machineDto.getMachineId());
                ResultSet tempRs3 = tempPstmt3.executeQuery();
                tempRs3.next();
                boolean isReserved = tempRs3.getInt(1) > 0;

                PreparedStatement tempPstmt4 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE RESERVE_MACHINE_ID = ? AND USER_ID = ?");
                tempPstmt4.setInt(1, machineDto.getMachineId());
                tempPstmt4.setInt(2, userId);
                ResultSet tempRs4 = tempPstmt4.executeQuery();
                tempRs4.next();
                boolean isReserving = tempRs4.getInt(1) > 0;

                machineViewDtos.add(new MachineViewDto(machineDto, isUsing, isDoing, isReserved, isReserving));

                tempPstmt1.close();
                tempPstmt2.close();
                tempPstmt3.close();
                tempPstmt4.close();
                tempRs1.close();
                tempRs2.close();
                tempRs3.close();
                tempRs4.close();
            }

            conn.commit();

            return machineViewDtos;
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

    public void requestMachine(String gymId, String machineId, String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.createConnection();

            // 유저가 해당 gym에 등록되어있는지 확인
            String sql = "SELECT * FROM GYM G JOIN MACHINE M ON G.Gym_id = M.Gym_id JOIN ENROLLS E ON E.Gym_id = G.Gym_id WHERE G.Gym_id = ? AND M.Machine_id = ? AND E.User_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gymId);
            pstmt.setString(2, machineId);
            pstmt.setString(3, userId);
            rs = pstmt.executeQuery();
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
            connectionMaker.closeAllWthPstmt(conn, pstmt, rs);
        }
    }

    public void reserveMachine(String gymId, String machineId, String userId) {
//            System.out.print("15. 운동기구 예약\n ");
//            sb.append("UPDATE USERS");
//		sb.append(" SET RESERVE_MACHINE_ID=" + machineId);
//		sb.append(" WHERE USER_ID=" + userId);

        //        sb.append("UPDATE MACHINE");
//		sb.append(" SET STATE='non_reservable'");
//		sb.append(" WHERE MACHINE_ID=" + machineId);

//         System.out.print("16. 운동기구 사용\n ");
//         sb.append("UPDATE USERS");
//		sb.append(" SET USING_MACHINE_ID=" + machineId);
//		sb.append(" WHERE USER_ID=" + userId);

        //        sb.append("UPDATE MACHINE");
//		sb.append(" SET STATE='" + newState + "'");
//		sb.append(" WHERE MACHINE_ID=" + machineId);
    }
}
