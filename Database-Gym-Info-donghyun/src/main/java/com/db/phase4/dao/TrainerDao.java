package com.db.phase4.dao;

import com.db.phase4.dto.review.ReviewViewDto;
import com.db.phase4.dto.trainer.FilteredTrainerViewDto;
import com.db.phase4.dto.trainer.TrainerViewDto;
import com.db.phase4.util.ConnectionMaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerDao {
    private final ConnectionMaker connectionMaker;

    public List<FilteredTrainerViewDto> findBySpecializationAndWorkYear(String special, int lowerYear, int upperYear) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<FilteredTrainerViewDto> trainers = new ArrayList<>();

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append(
                    "SELECT t.name, t.trainer_id, t.specialization, t.work_year, t.birth_date, t.sex, t.contact, g.name ");
            sb.append("FROM TRAINER t, GYM g ");
            sb.append("WHERE t.gym_id = g.gym_id AND "
                    + "T.Specialization = '" + special + "' AND T.Work_year BETWEEN "
                    + lowerYear + " AND " + upperYear);

            rs = stmt.executeQuery(sb.toString());

            while (rs.next()) {
                String name = rs.getString(1);
                int trainerId = rs.getInt(2);
                String specialization = rs.getString(3);
                int workYear = rs.getInt(4);
                LocalDate birthDate = rs.getDate(5).toLocalDate();
                String sex = rs.getString(6);
                String contact = rs.getString(7);
                String gymName = rs.getString(8);
                trainers.add(FilteredTrainerViewDto.builder()
                        .name(name)
                        .trainerId(trainerId)
                        .specialization(specialization)
                        .workYear(workYear)
                        .birthDate(birthDate)
                        .sex(sex)
                        .contact(contact)
                        .gymName(gymName)
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return trainers;
        }
    }

    public List<TrainerViewDto> findByGymId(int gymId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<TrainerViewDto> trainers = new ArrayList<>();
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT name, trainer_id, specialization, work_year, birth_date, sex ");
            sb.append("FROM trainer ");
            sb.append("WHERE gym_id =" + gymId);

            rs = stmt.executeQuery(sb.toString());

            while (rs.next()) {
                String name = rs.getString(1);
                int trainerId = rs.getInt(2);
                String specialization = rs.getString(3);
                int workYear = rs.getInt(4);
                LocalDate birthDate = rs.getDate(5).toLocalDate();
                String sex = rs.getString(6);
                trainers.add(TrainerViewDto.builder()
                        .name(name)
                        .trainerId(trainerId)
                        .specialization(specialization)
                        .workYear(workYear)
                        .birthDate(birthDate)
                        .sex(sex)
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return trainers;
        }
    }
}
