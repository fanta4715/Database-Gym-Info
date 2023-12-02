package com.db.phase4.dao;

import com.db.phase4.dto.gym.GymViewDto;
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
                GymViewDto gymViewDto = new GymViewDto(0, gymName, gymLocation, contact, 0);
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
}
