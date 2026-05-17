package com.ascsms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * ProductDAO handles all database operations related to Products.
 * This includes CRUD operations and stock-related queries.
 */

public class ProductDAO {
    // Scanner for user input
    Scanner sc = new Scanner(System.in);

    // ADD PRODUCT
    public void addProduct() {

        try {
            Connection conn = DBConnection.getConnection();// Get database connection
            System.out.println("\n===== ADD PRODUCT ====="); // Display header for adding product
            System.out.print("Product Name: "); // Get product details from user
            String name = sc.nextLine();

            System.out.print("Price: ");// Get product price input
            double price = sc.nextDouble();

            System.out.print("Quantity: ");// Get product quantity input
            int quantity = sc.nextInt();

            System.out.print("Low Stock Threshold: ");// Get low stock threshold input
            int threshold = sc.nextInt();

            // SQL query to insert product into database
            String sql =
                "INSERT INTO Products(product_name, price, quantity, low_stock_threshold) VALUES(?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            // Set values into query
            pst.setString(1, name);// Set product name
            pst.setDouble(2, price);// Set product price
            pst.setInt(3, quantity);// Set product quantity
            pst.setInt(4, threshold);// Set low stock threshold
            pst.executeUpdate();// Execute the insert query

            System.out.println("Product Added!");// Confirmation message

        } catch (SQLException e) {
            System.err.println("Product not added:" + e.getMessage());// Print error message if product addition fails
        }
    }

    // VIEW PRODUCTS
    public void viewProducts() {

        try {
            Connection conn = DBConnection.getConnection();// Get database connection
            String sql = "SELECT * FROM Products";// SQL query to select all products
            PreparedStatement pst = conn.prepareStatement(sql);// Prepare the SQL statement
            ResultSet rs = pst.executeQuery();// Execute the query and get the result set

            System.out.println("\n===== PRODUCTS =====");// Display header for products list

            while (rs.next()) {

                // Print product details in a formatted manner
                System.out.println(
                    rs.getInt("product_id") + " | " +
                    rs.getString("product_name") + " | " +
                    rs.getDouble("price") + " | Qty: " +
                    rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while fetching products: " + e.getMessage());// Print error message if fetching products fails
        }
    }

    // SEARCH PRODUCT
    public void searchProduct() {

        // Get database connection and search for products based on user input keyword
        try {
            Connection conn = DBConnection.getConnection();// Get database connection
            System.out.println("\n===== SEARCH PRODUCT =====");// Display header for product search
            System.out.print("Enter keyword: ");// Get search keyword input
            String keyword = sc.nextLine();// Read the keyword input

            String sql =
                "SELECT * FROM Products WHERE product_name LIKE ?";// SQL query to search for products by name using a wildcard
            PreparedStatement pst = conn.prepareStatement(sql);// Prepare the SQL statement
            pst.setString(1, "%" + keyword + "%");// Set the keyword with wildcards for the LIKE clause
            ResultSet rs = pst.executeQuery();// Execute the query and get the result set

            while (rs.next()) {
                // Print matching product details in a formatted manner
                System.out.println(
                    rs.getInt("product_id") + " | " +
                    rs.getString("product_name") + " | " +
                    rs.getDouble("price") + " | Qty: " +
                    rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while searching for products: " + e.getMessage());// Print error message if product search fails
        }
    }

    // UPDATE PRODUCT
    public void updateProduct() {

        try {
            Connection conn = DBConnection.getConnection();// Get database connection
            System.out.println("\n===== UPDATE PRODUCT =====");// Display header for product update
            System.out.print("Product ID: ");// Get product ID input
            int id = sc.nextInt();// Read the product ID input
            sc.nextLine();

            System.out.print("New Name: ");// Get new product name input
            String name = sc.nextLine();

            System.out.print("New Price: ");// Get new product price input
            double price = sc.nextDouble();

            String sql =
                "UPDATE Products SET product_name=?, price=? WHERE product_id=?";// SQL query to update product details based on product ID
            PreparedStatement pst = conn.prepareStatement(sql);// Prepare the SQL statement

            pst.setString(1, name);// Set new product name
            pst.setDouble(2, price);// Set new product price
            pst.setInt(3, id);// Set product ID for the WHERE clause
            pst.executeUpdate();// Execute the update query

            System.out.println("Product Updated!");// Confirmation message

        } catch (SQLException e) {
            System.err.println("Error occurred while updating product: " + e.getMessage());// Print error message if product update fails
        }
    }

    // DELETE PRODUCT
    public void deleteProduct() {

         try {
        Connection conn = DBConnection.getConnection();// Get database connection
        System.out.println("\n===== DELETE PRODUCT =====");// Display header for product deletion
        System.out.print("Product ID: ");// Get product ID input for deletion
        int id = sc.nextInt();

        // 1. Delete the product
        String sqlDelete = "DELETE FROM Products WHERE product_id=?";// SQL query to delete a product based on product ID
        PreparedStatement pstDelete = conn.prepareStatement(sqlDelete);// Prepare the SQL statement for deletion
        pstDelete.setInt(1, id);// Set product ID for the WHERE clause in deletion query
        int rowsAffected = pstDelete.executeUpdate();// Execute the delete query and get the number of rows affected

        if (rowsAffected > 0) {
            // 2. Fix the counter so the NEXT insert uses the lowest available ID
            // We find the current MAX id and set the counter to that.
            String sqlReseed = "DECLARE @maxID INT = (SELECT ISNULL(MAX(product_id), 0) FROM Products); " +
                               "DBCC CHECKIDENT ('Products', RESEED, @maxID);";// SQL query to reset the auto-increment counter to the current maximum product ID
            PreparedStatement stReseed = conn.prepareStatement(sqlReseed);// Prepare the SQL statement to reset the counter
            stReseed.execute();// Execute the counter reset query

            System.out.println("Product Deleted!");// Confirmation message for successful deletion and counter reset
        } else {
            System.out.println("Product ID not found.");//  Print message if the specified product ID does not exist in the database
        }

    } catch (SQLException e) {
        System.err.println("Error occurred while deleting product: " + e.getMessage());
    }
    }

    // LOW STOCK ALERT
    public void lowStockAlert() {

        try {
            Connection conn = DBConnection.getConnection();// Get database connection

            String sql =
                "SELECT * FROM Products WHERE quantity <= low_stock_threshold";// SQL query to select products that are at or below their low stock threshold
            PreparedStatement pst = conn.prepareStatement(sql);// Prepare the SQL statement
            ResultSet rs = pst.executeQuery();// Execute the query and get the result set

            System.out.println("\n===== LOW STOCK ALERT =====");// Display header for low stock alerts

            while (rs.next()) {

                // Print low stock product details in a formatted manner
                System.out.println(
                    rs.getString("product_name") +
                    " LOW STOCK! Remaining: " +
                    rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while fetching low stock alerts: " + e.getMessage());// Print error message if fetching low stock alerts fails
        }
    }
}