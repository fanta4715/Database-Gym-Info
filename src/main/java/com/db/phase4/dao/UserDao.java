package com.db.phase4.dao;

import com.db.phase4.dto.trainer.FilteredTrainerViewDto;
import com.db.phase4.dto.trainer.TrainerRegisterDto;
import com.db.phase4.util.ConnectionMaker;

import java.sql.Connection;
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
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("UPDATE USERS");
            sb.append(" SET TRAINER_ID=" + dto.getTrainerId());
            sb.append(" WHERE USER_ID=" + dto.getUserId());

            stmt.executeUpdate(sb.toString());
        } catch (SQLException e) {
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
            log.info("trainerId: " + trainerId);
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
}
