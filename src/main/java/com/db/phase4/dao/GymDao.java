package com.db.phase4.dao;

import com.db.phase4.dto.gym.GymViewDto;
import com.db.phase4.dto.gym.PersonViewDto;
import com.db.phase4.dto.gym.TrainerViewDto;
import com.db.phase4.util.ConnectionMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GymDao {
    private final ConnectionMaker connectionMaker;

    //인원 수 기준으로 gym 정보를 찾아서 보여주는 것
    public List<GymViewDto> findByNumOfPeople(String numOfPeople) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<GymViewDto> gymList = new ArrayList<>();

        try {
            System.out.println("numOfPeople: " + numOfPeople);
            System.out.println("numOfPeople type: " + numOfPeople.getClass());

            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT Name, Location, Contact ");
            sb.append("FROM GYM ");
            sb.append("WHERE Capacity < " + numOfPeople + " ");

            System.out.println("GymDao, finByNumOfPeople: A");
            rs = stmt.executeQuery(sb.toString());
            System.out.println("GymDao, finByNumOfPeople: B");


            while (rs.next()) {
                String gymName = rs.getString(1);
                String gymLocation = rs.getString(2);
                String contact = rs.getString(3);
                GymViewDto gymViewDto = new GymViewDto(0, gymName, gymLocation, contact, 0, 0, 0, "");
                gymList.add(gymViewDto);
                System.out.println("In GymVDao, gymViewDto instance: " +
                        ", gymId: default" +
                        ", gymName: " + gymName +
                        ", gymLocation: " + gymLocation +
                        ", contact: " + contact +
                        ", capacity: default"
                );
            }
            System.out.println("GymDao, finByNumOfPeople: C");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gymList;
    }

    public List<TrainerViewDto> findByTrainerSpecialization(String[] specializations) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<TrainerViewDto> trainerList = new ArrayList<>();

        try {
            System.out.println("GymDao, [0]: " + specializations[0] + " [1]: " + specializations[1]);
            System.out.println("numOfPeople type: " + specializations.getClass());

            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();
            System.out.println();
            StringBuffer sb = new StringBuffer();
            if (specializations[1].trim().equals("")) {
                System.out.println("array length: 1");
                sb.append("SELECT G.Name as GYM_NAME, G.Gym_id as GYM_ID, T.Trainer_id, T.Name, T.Contact, T.Specialization ");
                sb.append("FROM TRAINER T JOIN GYM G ON T.Gym_id = G.Gym_id ");
                sb.append("WHERE T.Specialization = \'" + specializations[0] + "' ");

            } else {
                System.out.println("[1]: " + specializations[1]);
                System.out.println("array length: 2");
                sb.append("SELECT G.Name as GYM_NAME, G.Gym_id as GYM_ID, T.Trainer_id, T.Name, T.Contact, T.Specialization ");
                sb.append("FROM TRAINER T JOIN GYM G ON T.Gym_id = G.Gym_id ");
                sb.append("WHERE T.Specialization in (\'" + specializations[0] + "', \'" + specializations[1] + "') ");
            }

            System.out.println(sb.toString());
            System.out.println("GymDao, finByTrainerSpecialization: A");
            rs = stmt.executeQuery(sb.toString());
            System.out.println("GymDao, finByTrainerSpecialization: B");
            if (rs == null) System.out.println("rs is null");
            else {
                System.out.println("rs is not null");
                System.out.println(rs.next());
            }

            while (rs.next()) {
                System.out.println("in rs.next() while");
                String gymName = rs.getString(1);
                int gymId = rs.getInt(2);
                int trainerId = rs.getInt(3);
                String trainerName = rs.getString(4);
                String trainerContact = rs.getString(5);
                String specialization = rs.getString(6);
                TrainerViewDto trainerViewDto = new TrainerViewDto(gymName, gymId, trainerId, trainerName, trainerContact, specialization);
                trainerList.add(trainerViewDto);
                System.out.println("In GymDao, trainerViewDao instance: " +
                        ", gymName: " + gymId +
                        ", gymName: " + gymName +
                        ", trainerId: " + trainerId +
                        ", trainerName: " + trainerName +
                        ", specialization: " + specialization + " "
                );
            }
            System.out.println("GymDao, findByTrainerSpecialization: C");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trainerList;
    }

    public List<GymViewDto> findByReviewRate(String status) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<GymViewDto> gymList = new ArrayList<>();

        try {
            System.out.println("status: " + status);

            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ROUND(AVG(R.Rating), 2) AS AVG_RATE, G.GYM_ID, G.NAME, G.LOCATION ");
            sb.append("FROM GYM G ");
            sb.append("JOIN REVIEW R ON G.GYM_ID = R.GYM_ID ");
            sb.append("GROUP BY G.GYM_ID, G.NAME, G.LOCATION ");
            if (status.equals("high")) {//평균 평점 높은 순
                sb.append("ORDER BY AVG_RATE DESC ");
            } else if (status.equals("low")) {//평균 평점 낮은 순
                sb.append("ORDER BY AVG_RATE ASC ");
            }
            sb.append("FETCH FIRST 10 ROWS ONLY ");

            System.out.println("GymDao, finByNumOfPeople: A");
            rs = stmt.executeQuery(sb.toString());
            System.out.println("GymDao, finByNumOfPeople: B");


            while (rs.next()) {
                float gymAvgRate = rs.getFloat(1);
                String gymId = rs.getString(2);
                String gymName = rs.getString(3);
                String gymLocation = rs.getString(4);

                GymViewDto gymViewDto = new GymViewDto(0, gymName, gymLocation, "", 0, gymAvgRate, 0, "");
                gymList.add(gymViewDto);
                System.out.println("In GymVDao, gymViewDto instance: " +
                        ", gymAvgRate: " + gymAvgRate +
                        ", gymId: " + gymId +
                        ", gymName: " + gymName +
                        ", gymLocation: " + gymLocation
                );
            }
            System.out.println("GymDao, finByNumOfPeople: C");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gymList;
    }

    public List<GymViewDto> findByNumOfMachine(String status) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<GymViewDto> gymList = new ArrayList<>();

        try {
            System.out.println("status: " + status);

            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT G.Gym_id, g.name, COUNT(*) as Total_Machine ");
            sb.append("FROM MACHINE M JOIN GYM G ON M.Gym_id = G.Gym_id ");
            sb.append("JOIN REVIEW R ON G.GYM_ID = R.GYM_ID ");
            sb.append("GROUP BY G.Gym_id, G.name ");
            if (status.equals("high")) {//머신 개수 많은 GYM 순
                sb.append("ORDER BY Total_Machine DESC ");
            } else if (status.equals("low")) {//머신 개수 적은 GYM 순
                sb.append("ORDER BY Total_Machine ASC ");
            }
            sb.append("FETCH FIRST 10 ROWS ONLY ");

            System.out.println("GymDao, finByNumOfPeople: A");
            rs = stmt.executeQuery(sb.toString());
            System.out.println("GymDao, finByNumOfPeople: B");


            while (rs.next()) {
                //G.Gym_id, g.name, COUNT(*) as Total_Machine
                int gymId = rs.getInt(1);
                String gymName = rs.getString(2);
                int numOfMachine = rs.getInt(3);

                GymViewDto gymViewDto = new GymViewDto(gymId, gymName, "", "", 0, 0, numOfMachine, "");
                gymList.add(gymViewDto);
                System.out.println("In GymVDao, gymViewDto instance: " +
                        ", gymId: " + gymId +
                        ", gymName: " + gymName +
                        ", numOfMachine: " + numOfMachine
                );
            }
            System.out.println("GymDao, finByNumOfPeople: C");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gymList;
    }

    public List<GymViewDto> findByNameOfGymAndUser(String status) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<GymViewDto> gymList = new ArrayList<>();

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT G.Name as Gym_Name, U.Name as User_Name ");
            sb.append("FROM USERS U, GYM G, ENROLLS E ");
            sb.append("WHERE U.User_id = E.User_id AND E.Gym_id = G.Gym_id ");
            if (status.equals("high")) {
                sb.append("ORDER BY G.Name DESC, U.Name DESC ");
            } else if (status.equals("low")) {
                sb.append("ORDER BY G.Name ASC, U.Name ASC ");
            }

            System.out.println("GymDao, finByNumOfPeople: A");
            rs = stmt.executeQuery(sb.toString());
            System.out.println("GymDao, finByNumOfPeople: B");


            while (rs.next()) {
                //G.Gym_id, g.name, COUNT(*) as Total_Machine
                String gymName = rs.getString(1);
                String userName = rs.getString(2);

                GymViewDto gymViewDto = new GymViewDto(0, gymName, "", "", 0, 0, 0, userName);
                gymList.add(gymViewDto);
                System.out.println("In GymVDao, gymViewDto instance: " +
                        ", gymName: " + gymName +
                        ", userName: " + userName
                );
            }
            System.out.println("GymDao, finByNumOfPeople: C");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gymList;
    }

    public List<PersonViewDto> findByPersonName() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<PersonViewDto> personList = new ArrayList<>();

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT Name FROM USERS ");
            sb.append("UNION ");
            sb.append("SELECT Name FROM TRAINER ");

            System.out.println("GymDao, finByNumOfPeople: A");
            rs = stmt.executeQuery(sb.toString());
            System.out.println("GymDao, finByNumOfPeople: B");


            while (rs.next()) {
                //G.Gym_id, g.name, COUNT(*) as Total_Machine
                String personName = rs.getString(1);

                PersonViewDto personViewDto = new PersonViewDto(personName);
                personList.add(personViewDto);
                System.out.println("In GymDao, personViewDto instance: " +
                        ", personName: " + personName
                );
            }
            System.out.println("GymDao, finByNumOfPeople: C");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personList;
    }

    public List<com.db.phase4.dto.GymViewDto> findAll(int userId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<com.db.phase4.dto.GymViewDto> gyms = new ArrayList<>();
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

//            String name;
//            String location;
//            String contact;
//            int capacity;
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT g.gym_id, g.name, g.location, g.contact, g.capacity ");
            sb.append("FROM GYM g, ENROLLS e ");
            sb.append("WHERE g.gym_id = e.gym_id AND e.user_id =" + userId);

            rs = stmt.executeQuery(sb.toString());

            while (rs.next()) {
                int gymId = rs.getInt(1);
                String name = rs.getString(2);
                String location = rs.getString(3);
                String contact = rs.getString(4);
                int capacity = rs.getInt(5);
                gyms.add(com.db.phase4.dto.GymViewDto.builder()
                        .gymId(gymId)
                        .name(name)
                        .contact(contact)
                        .location(location)
                        .capacity(capacity)
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return gyms;
        }
    }

}
