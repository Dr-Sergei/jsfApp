package org.directory;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class test {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/register";
        String username = "root";
        String dbPassword = "admin";
        String salt= BCrypt.gensalt();
        System.out.println(salt);

        // SQL query to validate user credentials
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, username, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println("connected!!");
            stmt.setString(1, "dimon");
            stmt.setString(2, "aaa"); // Assuming the password is hashed before passing it

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Пользователь " + "dimon" + ", добро пожаловать в наш магазин !");

                } else {
                    System.out.println("etwas went wrong");
                }
            }
        } catch (SQLException e) {
            // Handle database connection or query errors
            e.printStackTrace();
            System.out.println("failed to connect");; // Redirect to error page
        }
    }
    }

