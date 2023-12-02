package com.db.phase4.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.stereotype.Component;

@Component
public class ConnectionMaker {
    String ID = "GYM";
    String PW = "COMP322";
    String URL = "jdbc:oracle:thin:@localhost:1521:orcl";

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, ID, PW);
    }

    public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
