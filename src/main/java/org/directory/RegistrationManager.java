package org.directory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

public class RegistrationManager {
    public enum RegistrationStatus {
        SUCCESS,
        FAILURE_USERNAME_EXISTS,
        FAILURE_GENERAL
    }
    public static boolean registerUser(String username, String password, String email) {
        try (Connection connection = DatabaseManager.getConnection()) {
            // Generate salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // Hash the password
            String hashedPassword = PasswordUtils.generateHash(password, salt);

            // Store username, hashed password, and salt in the database
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (username, password, salt, email) VALUES (?, ?, ?, ?)");
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setBytes(3, salt);
            statement.setString(4, email);

            //give back if at least one row was created in sql (true if yes falls if no)
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

