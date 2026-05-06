package com.ascsms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SalesDAO {

    Scanner sc = new Scanner(System.in);

    // SELL PRODUCT
    public void sellProduct() {

        try {
            Connection conn = DBConnection.getConnection();

            System.out.print("Product ID: ");
            int productId = sc.nextInt();

            System.out.print("Quantity: ");
            int quantity = sc.nextInt();

            // CHECK STOCK
            String checkSql =
                "SELECT * FROM Products WHERE product_id=?";

            PreparedStatement checkPst =
                conn.prepareStatement(checkSql);

            checkPst.setInt(1, productId);

            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {

                int stock = rs.getInt("quantity");
                double price = rs.getDouble("price");

                // PREVENT INSUFFICIENT STOCK
                if (quantity > stock) {

                    System.out.println("Insufficient Stock!");
                    return;
                }

                double total = quantity * price;

                // DEDUCT STOCK
                String updateSql =
                    "UPDATE Products SET quantity = quantity - ? WHERE product_id=?";

                PreparedStatement updatePst =
                    conn.prepareStatement(updateSql);

                updatePst.setInt(1, quantity);
                updatePst.setInt(2, productId);

                updatePst.executeUpdate();

                // SAVE SALE
                String saleSql =
                    "INSERT INTO Sales(product_id, quantity_sold, total_price) VALUES(?,?,?)";

                PreparedStatement salePst =
                    conn.prepareStatement(saleSql);

                salePst.setInt(1, productId);
                salePst.setInt(2, quantity);
                salePst.setDouble(3, total);

                salePst.executeUpdate();

                System.out.println("Sale Successful!");
                System.out.println("Total: " + total);

            } else {

                System.out.println("Product Not Found!");
            }

        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    // DAILY SALES REPORT
    public void dailySalesReport() {

        try {
            Connection conn = DBConnection.getConnection();

            String sql =
                "SELECT CAST(sale_date AS DATE) AS sale_day, " +
                "SUM(total_price) AS total_sales " +
                "FROM Sales " +
                "GROUP BY CAST(sale_date AS DATE)";

            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            System.out.println("\n===== DAILY SALES REPORT =====");

            while (rs.next()) {

                System.out.println(
                    rs.getDate("sale_day") +
                    " | Total Sales: " +
                    rs.getDouble("total_sales")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    // WEEKLY SALES REPORT
    public void weeklySalesReport() {

        try {
            Connection conn = DBConnection.getConnection();

            String sql =
                "SELECT DATEPART(WEEK, sale_date) AS week_no, " +
                "SUM(total_price) AS total_sales " +
                "FROM Sales " +
                "GROUP BY DATEPART(WEEK, sale_date)";

            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            System.out.println("\n===== WEEKLY SALES REPORT =====");

            while (rs.next()) {

                System.out.println(
                    "Week " +
                    rs.getInt("week_no") +
                    " | Total Sales: " +
                    rs.getDouble("total_sales")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}