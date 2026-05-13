package com.ascsms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * StockDAO handles all database operations related to stock management.
 * This includes adding stock to products and maintaining stock history.
 */

public class StockDAO {

    Scanner sc = new Scanner(System.in);

    // STOCK IN
    public void stockIn() {

        try {
            Connection conn = DBConnection.getConnection();// Get database connection

            System.out.println("\n===== ADD PRODUCT STOCK =====");// Display header for adding product stock
            System.out.print("Product ID: ");// Get product ID input
            int productId = sc.nextInt();

            System.out.print("Quantity Added: ");// Get quantity to add input
            int quantity = sc.nextInt();

            // UPDATE PRODUCT STOCK
            String updateSql =
                "UPDATE Products SET quantity = quantity + ? WHERE product_id=?";// SQL query to update the product stock by adding the specified quantity
            PreparedStatement pst1 = conn.prepareStatement(updateSql);// Prepare the SQL statement
            pst1.setInt(1, quantity);// Set quantity to add
            pst1.setInt(2, productId);// Set product ID for the WHERE clause
            pst1.executeUpdate();// Execute the update query to add stock

            // SAVE STOCK HISTORY
            String insertSql =
                "INSERT INTO StockIn(product_id, quantity_added) VALUES(?,?)";// SQL query to insert a new stock in record into the StockIn table
            PreparedStatement pst2 = conn.prepareStatement(insertSql);
            pst2.setInt(1, productId);// Set product ID for the insert query to save stock in history
            pst2.setInt(2, quantity);// Set values for the insert query to save stock in history
            pst2.executeUpdate();// Execute the insert query to save stock in history

            System.out.println("Stock Added!");// Confirmation message

        } catch (SQLException e) {
            System.err.println("Error occurred while adding stock: " + e.getMessage());// Print error message if any SQL exception occurs during the stock in process
        }
    }
}
