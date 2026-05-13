package com.ascsms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserDAO handles all database operations related to user authentication and management.
 * This includes logging in users and retrieving their roles.
 */

public class UserDAO {

    public String login(String username, String password) {

        String role = null;// Variable to store the user's role after successful login

        try {
            Connection conn = DBConnection.getConnection();// Get database connection

            String sql =
                "SELECT role FROM Users WHERE username=? AND password=?";// SQL query to select the user's role based on the provided username and password
            PreparedStatement pst = conn.prepareStatement(sql);// Prepare the SQL statement
            pst.setString(1, username);// Set username parameter in the SQL query
            pst.setString(2, password);// Set password parameter in the SQL query
            ResultSet rs = pst.executeQuery();// Execute the query and get the result set

            if (rs.next()) {
                role = rs.getString("role");// If a matching user is found, retrieve their role from the result set
            }

        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());// Print error message if any SQL exception occurs during the login process
        }

        return role;// Return the user's role if login is successful, otherwise return null
    }
}