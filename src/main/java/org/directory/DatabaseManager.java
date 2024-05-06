package org.directory;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://172.17.0.3:3306/register";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Laden des JDBC-Treibers
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

