package com.db.phase4.dao;

import com.db.phase4.dto.trainer.TrainerRegisterDto;
import com.db.phase4.util.ConnectionMaker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
