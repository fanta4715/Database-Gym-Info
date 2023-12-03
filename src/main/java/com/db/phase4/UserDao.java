//package com.db.phase4;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//@Component
//public class UserDao {
//    String ID = "GYM";
//    String PW = "COMP322";
//    String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
//
//    Connection conn = DriverManager.getConnection(URL, ID, PW);
//    Statement stmt = conn.createStatement();
//    public ResultSet selectAllUsers() throws SQLException {
//        String sql = "SELECT * FROM users";
//        ResultSet rs = stmt.executeQuery(sql);
//        return rs;
//    }
//    public UserDao() throws SQLException {
//    }
//}
