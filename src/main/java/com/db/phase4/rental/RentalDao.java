package com.db.phase4.rental;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RentalDao {
    @Value("${TEST_ID}")
    String ID;
    @Value("${TEST_PW}")
    String PW ;
    @Value("${TEST_URL}")
    String URL;

    public List<RentalDto> getWtihGymId(String gymId){
        //print id, pw, url
        System.out.println(ID);
        System.out.println(PW);
        System.out.println(URL);

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, ID, PW);

            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            String sql = "SELECT * FROM GYM G JOIN RENTAL_ITEM R ON G.Gym_id = R.Gym_id WHERE G.Gym_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gymId);
            ResultSet rs = pstmt.executeQuery();

            // 트랜잭션 커밋
            conn.commit();

            // List<RentalDto> 결과 반환
            return RentalDto.of(rs);

        } catch (SQLException e) {
            // 예외 발생 시 롤백
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // 연결 닫기
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
