package com.db.phase4.dao;

import com.db.phase4.dto.review.ReviewContentDto;
import com.db.phase4.dto.review.ReviewCountDto;
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
            String sql = "DELETE FROM review WHERE review_id ="+reviewId;
            stmt.executeUpdate(sql);

            conn.commit();
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
            connectionMaker.closeAll(conn, stmt, rs);

        }

    }


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
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
            conn.commit();
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
            conn.commit();
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
            connectionMaker.closeAll(conn, stmt, rs);
        }
    }

    public ReviewContentDto findById(int reviewId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ReviewContentDto reviewContentDto = null;
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT r.user_comment, r.rating ");
            sb.append("FROM review r ");
            sb.append("WHERE r.review_id ="+reviewId);

            rs = stmt.executeQuery(sb.toString());

            rs.next();
            String comment = rs.getString(1);
            int rating = rs.getInt(2);
            reviewContentDto = new ReviewContentDto(rating, comment);
            log.info("reviewContentDto: "+reviewContentDto);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return reviewContentDto;
        }
    }

    public List<ReviewCountDto> findByGenderAndAge(String gender, LocalDate lowerBirthday, LocalDate upperBirthday) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<ReviewCountDto> reviewCounts = new ArrayList<>();
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT U.Name, TO_CHAR(U.Birth_date,'YYYY-MM-DD') as BIRTHDAY,U.Sex, COUNT(*) as Review_COUNT ");
            sb.append("FROM REVIEW R JOIN USERS U ON R.User_id = U.User_id ");
            sb.append("WHERE U.Sex = '"+gender+"' AND U.Birth_date < TO_DATE('"+upperBirthday+"','YYYY-MM-DD')");
            sb.append("AND U.Birth_date > TO_DATE('"+lowerBirthday+"','YYYY-MM-DD') ");
            sb.append("GROUP BY U.Name, TO_CHAR(U.Birth_date,'YYYY-MM-DD'),U.Sex ");
            sb.append("ORDER BY COUNT(*) ");

            rs = stmt.executeQuery(sb.toString());

            while(rs.next()){
                String name = rs.getString(1);
                LocalDate birthdate = rs.getDate(2).toLocalDate();
                String sex = rs.getString(3);
                int reviewCount = rs.getInt(4);

                reviewCounts.add(ReviewCountDto.builder()
                        .birthdate(birthdate)
                        .reviewCount(reviewCount)
                        .name(name)
                        .sex(sex)
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return reviewCounts;
        }
    }

    public int findUserIdById(int reviewId) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int reviewerId = 0;
        try {
            conn = connectionMaker.createConnection();
            stmt = conn.createStatement();

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT r.user_id ");
            sb.append("FROM review r ");
            sb.append("WHERE r.review_id ="+reviewId);

            rs = stmt.executeQuery(sb.toString());
            rs.next();
            reviewerId = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionMaker.closeAll(conn, stmt, rs);
            return reviewerId;
        }
    }


}
