package com.ascsms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class StockDAO {

    Scanner sc = new Scanner(System.in);

    // STOCK IN
    public void stockIn() {

        try {
            Connection conn = DBConnection.getConnection();

            System.out.print("Product ID: ");
            int productId = sc.nextInt();

            System.out.print("Quantity Added: ");
            int quantity = sc.nextInt();

            // UPDATE PRODUCT STOCK
            String updateSql =
                "UPDATE Products SET quantity = quantity + ? WHERE product_id=?";

            PreparedStatement pst1 = conn.prepareStatement(updateSql);

            pst1.setInt(1, quantity);
            pst1.setInt(2, productId);

            pst1.executeUpdate();

            // SAVE STOCK HISTORY
            String insertSql =
                "INSERT INTO StockIn(product_id, quantity_added) VALUES(?,?)";

            PreparedStatement pst2 = conn.prepareStatement(insertSql);

            pst2.setInt(1, productId);
            pst2.setInt(2, quantity);

            pst2.executeUpdate();

            System.out.println("Stock Added!");

        } catch (SQLException e) {
            System.err.println("Error occurred while adding stock: " + e.getMessage());
        }
    }
}
