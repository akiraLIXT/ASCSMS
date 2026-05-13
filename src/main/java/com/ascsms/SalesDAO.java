package com.ascsms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * SalesDAO handles all database operations related to sales transactions.
 * This includes selling products and generating sales reports.
 */

public class SalesDAO {

    Scanner sc = new Scanner(System.in);

    // SELL PRODUCT
    public void sellProduct() {

        try {
            Connection conn = DBConnection.getConnection();// Get database connection

            System.out.println("\n===== SELL PRODUCT =====");// Display header for selling product
            System.out.print("Product ID: ");// Get product ID input
            int productId = sc.nextInt();// Read the product ID input

            System.out.print("Quantity: ");// Get quantity input
            int quantity = sc.nextInt();// Read the quantity input

            // CHECK STOCK
            String checkSql =
                "SELECT * FROM Products WHERE product_id=?";// SQL query to check if the product exists and get its current stock and price
            PreparedStatement checkPst =
                conn.prepareStatement(checkSql);// Prepare the SQL statement
            checkPst.setInt(1, productId);// Set product ID for the WHERE clause
            ResultSet rs = checkPst.executeQuery();// Execute the query and get the result set

            if (rs.next()) {
                // If product exists, get current stock and price
                int stock = rs.getInt("quantity");
                double price = rs.getDouble("price");

                // PREVENT INSUFFICIENT STOCK
                if (quantity > stock) {
                    // If requested quantity exceeds available stock, print error message and return
                    System.out.println("Insufficient Stock!");// Print error message
                    return;
                }

                double total = quantity * price;// Calculate total price for the sale

                // DEDUCT STOCK
                String updateSql =
                    "UPDATE Products SET quantity = quantity - ? WHERE product_id=?";// SQL query to update the product stock by deducting the sold quantity
                PreparedStatement updatePst =
                    conn.prepareStatement(updateSql);// Prepare the SQL statement
                updatePst.setInt(1, quantity);// Set quantity to deduct
                updatePst.setInt(2, productId);// Set product ID for the WHERE clause
                updatePst.executeUpdate();// Execute the update query to deduct stock

                // SAVE SALE
                String saleSql =
                    "INSERT INTO Sales(product_id, quantity_sold, total_price) VALUES(?,?,?)";// SQL query to insert a new sale record into the Sales table
                PreparedStatement salePst =
                    conn.prepareStatement(saleSql);// Prepare the SQL statement
                salePst.setInt(1, productId);// Set product ID for the sale record
                salePst.setInt(2, quantity);// Set quantity sold for the sale record
                salePst.setDouble(3, total);// Set total price for the sale record
                salePst.executeUpdate();// Execute the insert query to save the sale record

                System.out.println("Sale Successful!");// Confirmation message
                System.out.println("Total: " + total);// Print total price for the sale

            } else {

                System.out.println("Product Not Found!");// If product ID does not exist, print error message
            }

        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());// Print error message if any SQL exception occurs during the selling process
        }
    }

    // DAILY SALES REPORT
    public void dailySalesReport() {

        try {
            Connection conn = DBConnection.getConnection();// Get database connection

            String sql =
                "SELECT CAST(sale_date AS DATE) AS sale_day, " +
                "SUM(total_price) AS total_sales " +
                "FROM Sales " +
                "GROUP BY CAST(sale_date AS DATE)";// SQL query to calculate total sales grouped by each day
            PreparedStatement pst = conn.prepareStatement(sql);// Prepare the SQL statement
            ResultSet rs = pst.executeQuery();// Execute the query and get the result set

            System.out.println("\n===== DAILY SALES REPORT =====");// Display header for daily sales report

            while (rs.next()) {
                
                // For each day, print the date and total sales for that day
                System.out.println(
                    rs.getDate("sale_day") +
                    " | Total Sales: " +
                    rs.getDouble("total_sales")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());// Print error message if any SQL exception occurs during the generation of the daily sales report
        }
    }

    // WEEKLY SALES REPORT
    public void weeklySalesReport() {

        try {
            Connection conn = DBConnection.getConnection();// Get database connection

            String sql =
                "SELECT DATEPART(WEEK, sale_date) AS week_no, " +
                "SUM(total_price) AS total_sales " +
                "FROM Sales " +
                "GROUP BY DATEPART(WEEK, sale_date)";// SQL query to calculate total sales grouped by each week number
            PreparedStatement pst = conn.prepareStatement(sql);// Prepare the SQL statement
            ResultSet rs = pst.executeQuery();// Execute the query and get the result set

            System.out.println("\n===== WEEKLY SALES REPORT =====");// Display header for weekly sales report

            while (rs.next()) {

                // For each week, print the week number and total sales for that week
                System.out.println(
                    "Week " +
                    rs.getInt("week_no") +
                    " | Total Sales: " +
                    rs.getDouble("total_sales")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());// Print error message if any SQL exception occurs during the generation of the weekly sales report
        }
    }
}