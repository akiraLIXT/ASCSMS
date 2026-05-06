package com.ascsms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
       "jdbc:sqlserver://localhost:1433;" + "databaseName=ASCMS;" + "encrypt=true;" + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "password123";

    public static Connection getConnection() {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("DATABASE CONNECTED!");

        } catch (SQLException e) {

            System.err.println("DATABASE CONNECTION FAILED: " + e.getMessage());
        }

        return conn;
    }
}
