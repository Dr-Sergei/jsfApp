package org.directory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

public class testlogin {
    public static void main(String[] args) throws SQLException {


        String name = "wasja";
        String password = "wasja";
        final String URL = "jdbc:mysql://172.17.0.3:3306/register";
        final String USERNAME = "root";
        final String PASSWORD = "admin";

        try (Connection connection = DatabaseManager.getConnection()) {

            // Retrieve stored hashed password and salt for the given username
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT password, salt FROM users WHERE username = ?");

            System.out.println("connected!!");

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                byte[] salt = resultSet.getBytes("salt");


                System.out.println("password: " + password);
                System.out.println("saltSQL: " + "0x978CB837326461EA11EAEF712FD6C5A5");
                System.out.println("saltJava: " + salt);
                System.out.println("storedPasswordSqL: " + "b8d34f12db6fffe1d3cc1b1f692477464bf2943199dc3784f46e072df8d94102");
                System.out.println("storedPasswordJAVA: " + storedPassword);


                // Verify password
                String hashedPassword = PasswordUtils.generateHash(password, salt);

                System.out.println("hashedPassword: " + hashedPassword);

                System.out.println("login succsessfull? " + hashedPassword.equals(storedPassword));
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
