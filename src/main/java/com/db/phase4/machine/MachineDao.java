package com.db.phase4.machine;

import com.db.phase4.rental.RentalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MachineDao {
    @Value("${TEST_ID}")
    String ID;
    @Value("${TEST_PW}")
    String PW;
    @Value("${TEST_URL}")
    String URL;

    public List<MachineDto> getWithGymId(String gymId) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, ID, PW);

            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            String sql = "SELECT * FROM GYM G JOIN MACHINE M ON G.Gym_id = M.Gym_id WHERE G.Gym_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gymId);
            ResultSet rs = pstmt.executeQuery();

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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }
}
