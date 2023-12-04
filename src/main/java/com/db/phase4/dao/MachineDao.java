package com.db.phase4.dao;

import com.db.phase4.dto.MachineDto;
import com.db.phase4.dto.MachineViewDto;
import com.db.phase4.dto.RentalItemViewDto;
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
    }

    public List<MachineDto> getWithGymId(String gymId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.createConnection();

            String sql = "SELECT * FROM MACHINE M JOIN GYM G ON G.Gym_id = M.Gym_id WHERE G.Gym_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gymId);
            rs = pstmt.executeQuery();

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
    }

    public List<MachineViewDto> myMachineInfo(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<MachineViewDto> machineList = new ArrayList<>();
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

//            int machineId;
//            String name;
//            String type;
//            String targetMuscle;
//            String state;

            //-----------------현재 유저의 예약/사용 머신 정보 가져오기------------------//
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT using_machine_id, reserve_machine_id ");
            sb.append("FROM users ");
            sb.append("WHERE user_id = " + userId);
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());

            int using_machine_id =-1; int reserve_machine_id=-1;

            while (rs.next()) {
                using_machine_id = rs.getInt(1);
                System.out.println(using_machine_id);
                reserve_machine_id = rs.getInt(2);
                System.out.println(reserve_machine_id);
            }

            //----------------현재 유저의 사용중인 머신 정보 가져와서 배열에 저장------------//
            if(using_machine_id != 0){
                sb = new StringBuffer();
                sb.append("SELECT * FROM MACHINE ");
                sb.append("WHERE MACHINE_ID = " + using_machine_id);
                rs = stmt.executeQuery(sb.toString());
                while (rs.next()) {
                    int machine_id = rs.getInt(1);
                    String name = rs.getString(2);
                    String type = rs.getString(3);
                    String targetMuscle = rs.getString(4);
                    String state = rs.getString(5);
                    MachineViewDto machineViewDto = new MachineViewDto(machine_id, name, type, targetMuscle, state);
                    machineList.add(machineViewDto);
                }
            }

            //----------------현재 유저의 예약중인 머신 정보 가져와서 배열에 저장------------//
            if(reserve_machine_id != 0){
                sb = new StringBuffer();
                sb.append("SELECT * FROM MACHINE ");
                sb.append("WHERE MACHINE_ID = " + reserve_machine_id);
                rs = stmt.executeQuery(sb.toString());
                while (rs.next()) {
                    int machine_id = rs.getInt(1);
                    String name = rs.getString(2);
                    String type = rs.getString(3);
                    String targetMuscle = rs.getString(4);
                    String state = rs.getString(5);
                    MachineViewDto machineViewDto = new MachineViewDto(machine_id, name, type, targetMuscle, state);
                    machineList.add(machineViewDto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return machineList;
        }
    }
}
