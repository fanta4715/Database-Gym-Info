package com.db.phase4.dao;

import com.db.phase4.dto.HealthInfoDto;
import com.db.phase4.dto.UserViewDto;
import com.db.phase4.dto.gym.UserDetailViewDto;
import com.db.phase4.dto.trainer.FilteredTrainerViewDto;
import com.db.phase4.dto.trainer.TrainerRegisterDto;
import com.db.phase4.util.ConnectionMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDao {
    private final ConnectionMaker connectionMaker;

    public void registerTrainer(TrainerRegisterDto dto) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.createConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            stmt = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement("SELECT TRAINER_ID FROM USERS WHERE USER_ID= ?");
            pstmt.setInt(1, dto.getUserId());
            ResultSet rs1 = pstmt.executeQuery();
            rs1.next();

            int myTrainerId= rs1.getInt(1);
            log.info("myTrainerId: " + myTrainerId);
            log.info("dto.getTrainerId(): " + dto.getTrainerId());
            StringBuffer sb = new StringBuffer();

            if (myTrainerId == dto.getTrainerId()) {
                sb.append("UPDATE USERS");
                sb.append(" SET TRAINER_ID = NULL");
                sb.append(" WHERE USER_ID=" + dto.getUserId());
            }

            else {
                sb.append("UPDATE USERS");
                sb.append(" SET TRAINER_ID=" + dto.getTrainerId());
                sb.append(" WHERE USER_ID=" + dto.getUserId());
            }
            stmt.executeUpdate(sb.toString());
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
            connectionMaker.closeAll(conn, stmt, rs);
        }

    }

    public int findPtTrainerId(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int trainerId = 0;
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT TRAINER_ID ");
            sb.append("FROM USERS ");
            sb.append("WHERE USER_ID=" + userId);

            rs = stmt.executeQuery(sb.toString());
            rs.next();
            trainerId = rs.getInt(1);
            log.info("지금 조회하는 유저의 trainerId: " + trainerId);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return trainerId;
        }
    }

    public FilteredTrainerViewDto findTrainerById(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        FilteredTrainerViewDto myTrainer = null;

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT t.name, t.trainer_id, t.specialization, t.work_year, t.birth_date, t.sex, t.contact ");
            sb.append("FROM TRAINER t, USERS u ");
            sb.append("WHERE u.trainer_id = t.trainer_id ");
            sb.append("AND u.user_id = " + userId);

            rs = stmt.executeQuery(sb.toString());
            rs.next();

            String name = rs.getString(1);
            int trainerId = rs.getInt(2);
            String specialization = rs.getString(3);
            int workYear = rs.getInt(4);
            LocalDate birthDate = rs.getDate(5).toLocalDate();
            String sex = rs.getString(6);
            String contact = rs.getString(7);

            myTrainer = FilteredTrainerViewDto.builder()
                    .name(name)
                    .trainerId(trainerId)
                    .specialization(specialization)
                    .workYear(workYear)
                    .birthDate(birthDate)
                    .sex(sex)
                    .contact(contact)
                    .gymName(null)
                    .build();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return myTrainer;
        }
    }

    public List<UserViewDto> findByGymId(int gymId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<UserViewDto> people = new ArrayList<>();

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT Name as USER_NAME, TO_CHAR(Birth_date,'YYYY-MM-DD') as USER_BIRTHDAY, sex ");
            sb.append("FROM ( ");
            sb.append("    SELECT U.Name, U.Birth_date, E.Gym_id, U.sex ");
            sb.append("    FROM USERS U JOIN ENROLLS E ON U.User_id = E.User_id ) ");
            sb.append("WHERE Gym_id =" + gymId);

            rs = stmt.executeQuery(sb.toString());
            while (rs.next()){
                String name = rs.getString(1);
                LocalDate birthDate = rs.getDate(2).toLocalDate();
                String sex = rs.getString(3);
                UserViewDto user = UserViewDto.builder()
                        .name(name)
                        .birthdate(birthDate)
                        .sex(sex)
                        .build();

                people.add(user);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return people;
        }
    }

    public UserViewDto findById(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        UserViewDto person = null;

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT U.Name, U.Birth_date, U.SEX FROM USERS U WHERE U.User_id = " + userId);
            String sql = sb.toString();

            rs = stmt.executeQuery(sb.toString());
            rs.next();
            String name = rs.getString(1);
            LocalDate birthDate = rs.getDate(2).toLocalDate();
            String sex = rs.getString(3);
            person = UserViewDto.builder()
                        .name(name)
                        .birthdate(birthDate)
                        .sex(sex)
                        .build();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return person;
        }


    }

    public List<HealthInfoDto> findHealthInfoById(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<HealthInfoDto> healthInfos = new ArrayList<>();

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT measure_date, weight, height, body_fat_percentage, muscle_mass ");
            sb.append("FROM HEALTH_INFO ");
            sb.append("WHERE user_id = " + userId);
            String sql = sb.toString();

            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
                LocalDate measureDate = rs.getDate(1).toLocalDate();
                float weight = rs.getFloat(2);
                float height = rs.getFloat(3);
                float bodyFatPercentage = rs.getFloat(4);
                float muscleMass = rs.getFloat(5);

                healthInfos.add(HealthInfoDto.builder()
                        .measureDate(measureDate)
                        .weight(weight)
                        .height(height)
                        .bodyFatPercentage(bodyFatPercentage)
                        .muscleMass(muscleMass)
                        .build());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return healthInfos;
        }

    }

    public boolean checkIfUserHasTrainer(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Boolean hasTrainer = false;

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT trainer_id ");
            sb.append("FROM USERS ");
            sb.append("WHERE user_id = " + userId);

            rs = stmt.executeQuery(sb.toString());
            rs.next();
            int trainerId = rs.getInt(1);
            if (trainerId != 0) {
                log.info("트레이너가 있습니다.");
                hasTrainer = true;
            }
            log.info("트레이너가 없습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return hasTrainer;
        }

    }

    //-------------추가 사항------------//
    // 유저 개인 정보 조회
    public UserDetailViewDto findByUserId(int id) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        UserDetailViewDto userDetailViewDto = null;

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT * ");
            sb.append("FROM USERS ");
            sb.append("WHERE USERS.User_id = "+ id);
            rs = stmt.executeQuery(sb.toString());
            while (rs.next()){
                int userId = rs.getInt(1);
                int usingMachineId = rs.getInt(2);
                int reserveMachineId = rs.getInt(3);
                int trainerId = rs.getInt(4);
                String userName = rs.getString(5);
                String birthDate = rs.getString(6);
                String sex = rs.getString(7);
                String contact = rs.getString(8);

                userDetailViewDto =  new UserDetailViewDto(userId, userName, usingMachineId, reserveMachineId, trainerId, birthDate, sex, contact, 0, "");
            }
            if(userDetailViewDto == null){
                System.out.println("userViewDto is null");
            }else{
                System.out.println("userViewDto: " + userDetailViewDto);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return userDetailViewDto;
        }
    }

    // 유저 개인 정보 수정
    public UserDetailViewDto modifyUserInfo(String[] userInfoArray) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        UserDetailViewDto userDetailViewDto = null;
//        String[] userInfoArray = {userId, userName, birthDate, contact};

        System.out.println("A");

        String Id = userInfoArray[0];
        String Name = userInfoArray[1];
        String Date = userInfoArray[2];
        String[] parts = Date.split("\\s+");
        // 분할된 문자열 중 첫 번째 부분을 선택
        Date = parts[0];
        String Phone = userInfoArray[3];
        System.out.println(userInfoArray);
        System.out.println("B");
        System.out.println(Id);
        System.out.println(Name);
        System.out.println(Date);
        System.out.println(Phone);
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("UPDATE USERS ");
            sb.append("SET Name = '" + Name + "', ");
            sb.append("Birth_date = TO_DATE('" + Date + "', 'YYYY-MM-DD'), ");
            sb.append("Phone_number = '" + Phone + "' ");
            sb.append("WHERE User_id  = " + Integer.parseInt(Id));
            System.out.println("C");

            rs = stmt.executeQuery(sb.toString());

            sb = new StringBuffer();
            sb.append("SELECT *");
            sb.append("FROM USERS ");
            sb.append("WHERE User_id = " + Integer.parseInt(Id));
            rs = stmt.executeQuery(sb.toString());

            System.out.println("D");

            while (rs.next()){
                int userId = rs.getInt(1);
                int usingMachineId = rs.getInt(2);
                int reserveMachineId = rs.getInt(3);
                int trainerId = rs.getInt(4);
                String userName = rs.getString(5);
                String birthDate = rs.getString(6);
                String sex = rs.getString(7);
                String contact = rs.getString(8);
                System.out.println("E");

                userDetailViewDto =  new UserDetailViewDto(userId, userName, usingMachineId, reserveMachineId, trainerId, birthDate, sex, contact, 0, "");
            }
            System.out.println("F");

            if(userDetailViewDto == null){
                System.out.println("userViewDto is null");
            }else{
                System.out.println("userViewDto: " + userDetailViewDto);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("G");

            connectionMaker.closeAll(conn, stmt, rs);
            return userDetailViewDto;
        }
    }
}
