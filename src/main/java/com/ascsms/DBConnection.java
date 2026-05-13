package com.ascsms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection handles the connection between the Java application
 * and the Microsoft SQL Server database.
 */

public class DBConnection {

    private static final String URL ="jdbc:sqlserver://localhost:1433;" + "databaseName=ASCMS;" + "encrypt=true;" + "trustServerCertificate=true;"; // JDBC URL for connecting to SQL Server

    // Database credentials (SQL Server login)
    private static final String USER = "sa";
    private static final String PASSWORD = "password123";

    // Prevents repeated "DATABASE CONNECTED!" messages
    private static boolean printed = false;

    /**
     * Establishes and returns a connection to the database.
     *
     * @return Connection object if successful, otherwise null
     */
    public static Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD); // Attempt to connect to the database

            if (!printed) {
                System.out.println("DATABASE CONNECTED!");// Print success message only once
                printed = true;
            }

        } catch (SQLException e) {
            System.err.println("DATABASE CONNECTION FAILED: " + e.getMessage());// Print error message if connection fails
        }

        return conn;
    }
}