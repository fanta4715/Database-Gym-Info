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

                // 해당 유저가 예약한 기구가 있는지(canReserve) + 해당 유저가 사용 중인 기구가 있는지(canUse)
                PreparedStatement tempPstmt5 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE USER_ID = ? AND RESERVE_MACHINE_ID IS NOT NULL");
                tempPstmt5.setInt(1, userId);
                ResultSet tempRs5 = tempPstmt5.executeQuery();
                tempRs5.next();
                boolean canReserve = tempRs5.getInt(1) == 0;

                PreparedStatement tempPstmt6 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE USER_ID = ? AND USING_MACHINE_ID IS NOT NULL");
                tempPstmt6.setInt(1, userId);
                ResultSet tempRs6 = tempPstmt6.executeQuery();
                tempRs6.next();
                boolean canUse = tempRs6.getInt(1) == 0;

                machineViewDtos.add(new MachineViewDto(machineDto, isUsing, isDoing, isReserved, isReserving, canUse, canReserve));

                tempPstmt1.close();
                tempPstmt2.close();
                tempPstmt3.close();
                tempPstmt4.close();
                tempPstmt5.close();
                tempPstmt6.close();
                tempRs1.close();
                tempRs2.close();
                tempRs3.close();
                tempRs4.close();
                tempRs5.close();
                tempRs6.close();
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

    public void reserveMachine(int gymId, int machineId, int userId, String reservation) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.createConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            switch (reservation) {
                case "reservation-reserve":
                    // 이미 해당 기구를 예약한 사람이 있는지 확인 -> 있다면 예약 불가
                    PreparedStatement tempPstmt1 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE RESERVE_MACHINE_ID = ?");
                    tempPstmt1.setInt(1, machineId);
                    ResultSet tempRs1 = tempPstmt1.executeQuery();
                    tempRs1.next();
                    if (tempRs1.getInt(1) > 0) {
                        tempPstmt1.close();
                        tempRs1.close();
                        break;
                    }

                    pstmt = conn.prepareStatement("UPDATE USERS SET RESERVE_MACHINE_ID = ? WHERE USER_ID = ?");
                    pstmt.setInt(1, machineId);
                    pstmt.setInt(2, userId);
                    pstmt.executeUpdate();
                    break;
                case "reservation-use":
                    // 본인이 예약자인 경우 진행
                    PreparedStatement tempPstmt2 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE RESERVE_MACHINE_ID = ? AND USER_ID = ?");
                    tempPstmt2.setInt(1, machineId);
                    tempPstmt2.setInt(2, userId);
                    ResultSet tempRs2 = tempPstmt2.executeQuery();
                    tempRs2.next();
                    if (tempRs2.getInt(1) == 0) {
                        tempPstmt2.close();
                        tempRs2.close();
                        break;
                    }

                    pstmt = conn.prepareStatement("UPDATE USERS SET USING_MACHINE_ID = ?, RESERVE_MACHINE_ID = NULL WHERE USER_ID = ?");
                    pstmt.setInt(1, machineId);
                    pstmt.setInt(2, userId);
                    pstmt.executeUpdate();
                    break;
                case "reservation-cancel":
                    // 본인이 예약자인 경우 진행
                    PreparedStatement tempPstmt3 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE RESERVE_MACHINE_ID = ? AND USER_ID = ?");
                    tempPstmt3.setInt(1, machineId);
                    tempPstmt3.setInt(2, userId);
                    ResultSet tempRs3 = tempPstmt3.executeQuery();
                    tempRs3.next();
                    if (tempRs3.getInt(1) == 0) {
                        tempPstmt3.close();
                        tempRs3.close();
                        break;
                    }

                    pstmt = conn.prepareStatement("UPDATE USERS SET RESERVE_MACHINE_ID = NULL WHERE USER_ID = ?");
                    pstmt.setInt(1, userId);
                    pstmt.executeUpdate();
                    break;
                case "use":
                    // 해당 기구를 사용하고 있는 사람이 있는지 확인 -> 있다면 사용 불가
                    PreparedStatement tempPstmt4 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE USING_MACHINE_ID = ?");
                    tempPstmt4.setInt(1, machineId);
                    ResultSet tempRs4 = tempPstmt4.executeQuery();
                    tempRs4.next();
                    if (tempRs4.getInt(1) > 0) {
                        tempPstmt4.close();
                        tempRs4.close();
                        break;
                    }

                    pstmt = conn.prepareStatement("UPDATE USERS SET USING_MACHINE_ID = ? WHERE USER_ID = ?");
                    pstmt.setInt(1, machineId);
                    pstmt.setInt(2, userId);
                    pstmt.executeUpdate();
                    break;
                case "done":
                    // 본인이 사용자인 경우 진행
                    PreparedStatement tempPstmt5 = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE USING_MACHINE_ID = ? AND USER_ID = ?");
                    tempPstmt5.setInt(1, machineId);
                    tempPstmt5.setInt(2, userId);
                    ResultSet tempRs5 = tempPstmt5.executeQuery();
                    tempRs5.next();
                    if (tempRs5.getInt(1) == 0) {
                        tempPstmt5.close();
                        tempRs5.close();
                        break;
                    }

                    pstmt = conn.prepareStatement("UPDATE USERS SET USING_MACHINE_ID = NULL WHERE USER_ID = ?");
                    pstmt.setInt(1, userId);
                    pstmt.executeUpdate();
                    break;
            }

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

    public boolean canUse(int gymId, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.createConnection();

            String sql = "SELECT COUNT(*) FROM USERS WHERE USER_ID = ? AND USING_MACHINE_ID IS NOT NULL";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) == 0;
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
        return false;
    }

    public boolean canReserve(int gymId, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.createConnection();

            String sql = "SELECT COUNT(*) FROM USERS WHERE USER_ID = ? AND RESERVE_MACHINE_ID IS NOT NULL";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) == 0;
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
        return false;
    }
}
