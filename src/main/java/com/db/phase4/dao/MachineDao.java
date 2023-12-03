package com.db.phase4.dao;

import com.db.phase4.dto.MachineViewDto;
import com.db.phase4.dto.RentalItemViewDto;
import com.db.phase4.util.ConnectionMaker;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
