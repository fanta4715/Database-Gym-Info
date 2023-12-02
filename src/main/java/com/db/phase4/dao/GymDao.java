package com.db.phase4.dao;

import com.db.phase4.dto.gym.GymViewDto;
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
    public List<GymViewDto> findByNumOfPeople(String numOfPeople){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<GymViewDto> gymList = new ArrayList<>();

        try{
            System.out.println("numOfPeople: " + numOfPeople);
            System.out.println("numOfPeople type: "+ numOfPeople.getClass());

            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT Name, Location, Contact ");
            sb.append("FROM GYM ");
            sb.append("WHERE Capacity < "  + numOfPeople + " ");

            System.out.println("GymDao, finByNumOfPeople: A");
            rs = stmt.executeQuery(sb.toString());
            System.out.println("GymDao, finByNumOfPeople: B");


            while(rs.next()){
                String gymName = rs.getString(1);
                String gymLocation = rs.getString(2);
                String contact = rs.getString(3);
                GymViewDto gymViewDto = new GymViewDto(0, gymName, gymLocation, contact, 0, 0);
                gymList.add(gymViewDto);
                System.out.println("In GymVDao, gymViewDto instance: " +
                        ", gymId: default" +
                        ", gymName: "+ gymName +
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

        try{
            System.out.println("GymDao, [0]: " + specializations[0] + " [1]: " + specializations[1]);
            System.out.println("numOfPeople type: " + specializations.getClass());

            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();
            System.out.println();
            StringBuffer sb = new StringBuffer();
            if(specializations[1].trim().equals("")){
                System.out.println("array length: 1");
                sb.append("SELECT G.Name as GYM_NAME, G.Gym_id as GYM_ID, T.Trainer_id, T.Name, T.Contact, T.Specialization ");
                sb.append("FROM TRAINER T JOIN GYM G ON T.Gym_id = G.Gym_id ");
                sb.append("WHERE T.Specialization = \'" + specializations[0] + "' ");

            }else{
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
            if(rs == null) System.out.println("rs is null");
            else {System.out.println("rs is not null");
                System.out.println(rs.next());}

            while(rs.next()){
                System.out.println("in rs.next() while");
                String gymName = rs.getString(1);
                int gymId = rs.getInt(2);
                int trainerId = rs. getInt(3);
                String trainerName = rs.getString(4);
                String trainerContact = rs.getString(5);
                String specialization = rs.getString(6);
                TrainerViewDto trainerViewDto = new TrainerViewDto(gymName, gymId, trainerId, trainerName, trainerContact, specialization);
                trainerList.add(trainerViewDto);
                System.out.println("In GymDao, trainerViewDao instance: " +
                        ", gymName: " + gymId +
                        ", gymName: "+ gymName +
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

        try{
            System.out.println("status: " + status);

            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ROUND(AVG(R.Rating), 2) AS AVG_RATE, G.GYM_ID, G.NAME, G.LOCATION ");
            sb.append("FROM GYM G ");
            sb.append("JOIN REVIEW R ON G.GYM_ID = R.GYM_ID ");
            sb.append("GROUP BY G.GYM_ID, G.NAME, G.LOCATION ");
            if(status.equals("high")){//평균 평점 높은 순
                sb.append("ORDER BY AVG_RATE DESC ");
            }else if(status.equals("low")){//평균 평점 낮은 순
                sb.append("ORDER BY AVG_RATE ASC ");
            }
            sb.append("FETCH FIRST 10 ROWS ONLY ");

            System.out.println("GymDao, finByNumOfPeople: A");
            rs = stmt.executeQuery(sb.toString());
            System.out.println("GymDao, finByNumOfPeople: B");


            while(rs.next()){
                float gymAvgRate = rs.getFloat(1);
                String gymId = rs.getString(2);
                String gymName = rs.getString(3);
                String gymLocation= rs.getString(4);

                GymViewDto gymViewDto = new GymViewDto(0, gymName, gymLocation, "", 0, gymAvgRate);
                gymList.add(gymViewDto);
                System.out.println("In GymVDao, gymViewDto instance: " +
                        ", gymAvgRate: " + gymAvgRate +
                        ", gymId: " +gymId +
                        ", gymName: "+ gymName +
                        ", gymLocation: " + gymLocation
                );
            }
            System.out.println("GymDao, finByNumOfPeople: C");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gymList;
    }
}
