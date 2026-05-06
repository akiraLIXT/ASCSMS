package com.ascsms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductDAO {

    Scanner sc = new Scanner(System.in);

    // ADD PRODUCT
    public void addProduct() {

        try {
            Connection conn = DBConnection.getConnection();

            System.out.print("Product Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = sc.nextDouble();

            System.out.print("Quantity: ");
            int quantity = sc.nextInt();

            System.out.print("Low Stock Threshold: ");
            int threshold = sc.nextInt();

            String sql =
                "INSERT INTO Products(product_name, price, quantity, low_stock_threshold) VALUES(?,?,?,?)";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, name);
            pst.setDouble(2, price);
            pst.setInt(3, quantity);
            pst.setInt(4, threshold);

            pst.executeUpdate();

            System.out.println("Product Added!");

        } catch (SQLException e) {
            System.err.println("Product not added:" + e.getMessage());
        }
    }

    // VIEW PRODUCTS
    public void viewProducts() {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM Products";

            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            System.out.println("\n===== PRODUCTS =====");

            while (rs.next()) {

                System.out.println(
                    rs.getInt("product_id") + " | " +
                    rs.getString("product_name") + " | " +
                    rs.getDouble("price") + " | Qty: " +
                    rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while fetching products: " + e.getMessage());
        }
    }

    // SEARCH PRODUCT
    public void searchProduct() {

        try {
            Connection conn = DBConnection.getConnection();

            System.out.print("Enter keyword: ");
            String keyword = sc.nextLine();

            String sql =
                "SELECT * FROM Products WHERE product_name LIKE ?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                System.out.println(
                    rs.getInt("product_id") + " | " +
                    rs.getString("product_name") + " | " +
                    rs.getDouble("price") + " | Qty: " +
                    rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while searching for products: " + e.getMessage());
        }
    }

    // UPDATE PRODUCT
    public void updateProduct() {

        try {
            Connection conn = DBConnection.getConnection();

            System.out.print("Product ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("New Name: ");
            String name = sc.nextLine();

            System.out.print("New Price: ");
            double price = sc.nextDouble();

            String sql =
                "UPDATE Products SET product_name=?, price=? WHERE product_id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, name);
            pst.setDouble(2, price);
            pst.setInt(3, id);

            pst.executeUpdate();

            System.out.println("Product Updated!");

        } catch (SQLException e) {
            System.err.println("Error occurred while updating product: " + e.getMessage());
        }
    }

    // DELETE PRODUCT
    public void deleteProduct() {

        try {
            Connection conn = DBConnection.getConnection();

            System.out.print("Product ID: ");
            int id = sc.nextInt();

            String sql =
                "DELETE FROM Products WHERE product_id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, id);

            pst.executeUpdate();

            System.out.println("Product Deleted!");

        } catch (SQLException e) {
            System.err.println("Error occurred while deleting product: " + e.getMessage());
        }
    }

    // LOW STOCK ALERT
    public void lowStockAlert() {

        try {
            Connection conn = DBConnection.getConnection();

            String sql =
                "SELECT * FROM Products WHERE quantity <= low_stock_threshold";

            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            System.out.println("\n===== LOW STOCK ALERT =====");

            while (rs.next()) {

                System.out.println(
                    rs.getString("product_name") +
                    " LOW STOCK! Remaining: " +
                    rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while fetching low stock alerts: " + e.getMessage());
        }
    }
}