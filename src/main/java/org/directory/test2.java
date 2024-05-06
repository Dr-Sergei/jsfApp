package org.directory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class test2 {
    public static void main(String[] args) throws SQLException {
        String name = "admin";
        String password = "loli";
        String email = "www@yandex.ru";
        final String URL = "jdbc:mysql://localhost:3306/register";
        final String USERNAME = "root";
        final String PASSWORD = "admin";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Generate salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // Hash the password
            String hashedPassword = PasswordUtils.generateHash(password, salt);

            // Store username, hashed password, and salt in the database
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (username, password, salt, email) VALUES (?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, hashedPassword);
            statement.setBytes(3, salt);
            statement.setString(4, email);

            int rowsInserted = statement.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
    }
}


