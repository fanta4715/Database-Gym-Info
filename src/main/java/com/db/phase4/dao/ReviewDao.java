package com.db.phase4.dao;

import com.db.phase4.dto.review.ReviewSaveReq;
import com.db.phase4.dto.review.ReviewUpdateReq;
import com.db.phase4.dto.review.ReviewViewDto;
import com.db.phase4.util.ConnectionMaker;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewDao {
    private final ConnectionMaker connectionMaker;

    public void deleteReview(int reviewId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();
            String sql = "DELETE FROM reviews WHERE review_id ="+reviewId;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
        }

    }

    public void findAll() throws SQLException {
        Connection conn = connectionMaker.createConnection();
        Statement stmt = conn.createStatement();

        String sql = "SELECT * FROM review";
        stmt.executeQuery(sql);
    }

/*
    int reviewId;
    String comment;
    int rating;
    LocalDate date;
    String userName;
 */
    public List<ReviewViewDto> findByGymId(int gymId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<ReviewViewDto> reviews = new ArrayList<>();
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT r.review_id, r.user_comment, r.rating, r.created_date, u.name ");
            sb.append("FROM review r, users u ");
            sb.append("WHERE r.user_id = u.user_id AND r.gym_id ="+gymId);

            rs = stmt.executeQuery(sb.toString());

            while(rs.next()){
                int reviewId = rs.getInt(1);
                String comment = rs.getString(2);
                int rating = rs.getInt(3);
                LocalDate date = rs.getDate(4).toLocalDate();
                String userName = rs.getString(5);
                reviews.add(new ReviewViewDto(reviewId, comment, rating, date, userName));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return reviews;
        }
    }

    public void save(ReviewSaveReq reviewReq) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        log.info("save!!");
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            LocalDate currentDate = LocalDate.now();

            // 날짜를 원하는 형식으로 포맷팅
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO REVIEW(Review_id, Rating, User_Comment, Created_date, User_id, Gym_id) ");
            sb.append(
                    "VALUES ((Select Review_id from (SELECT Review_id from review order by Review_id desc) where ROWNUM = 1)+1,"
                            + reviewReq.getRating() + ", '" + reviewReq.getComment() + "', " + "TO_DATE('" + formattedDate + "', 'YYYY-MM-DD'), " + reviewReq.getUserId()
                            + ", " + reviewReq.getGymId() + ")");

            stmt.executeUpdate(sb.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
        }
    }

    public void update(ReviewUpdateReq reviewReq) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            // 날짜를 원하는 형식으로 포맷팅
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

            sb.append("UPDATE REVIEW ");
            sb.append("SET Rating = " + reviewReq.getRating() + ", ");
            sb.append("    User_comment = '" + reviewReq.getComment() + "', ");
            sb.append("    Created_date = TO_DATE('" + formattedDate + "', 'YYYY-MM-DD') ");
            sb.append(" WHERE Review_id = " + reviewReq.getReviewId());

            stmt.executeUpdate(sb.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
        }
    }
}
