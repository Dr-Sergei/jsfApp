package org.directory;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {
    public static boolean loginUser(String username, String password) {
        try (Connection connection = DatabaseManager.getConnection()) {

            // Retrieve stored hashed password and salt for the given username
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT password, salt FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                byte[] salt = resultSet.getBytes("salt");

                // Verify password
                String hashedPassword = PasswordUtils.generateHash(password, salt);
                return hashedPassword.equals(storedPassword);
            } else return false;
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

