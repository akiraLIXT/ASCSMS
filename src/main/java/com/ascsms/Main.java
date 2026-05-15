package com.ascsms;

import java.util.Scanner;

public class Main {

    /**
    * Main class - entry point of the ASCMS (Automated School Canteen Management System)
    * Handles login and menu navigation for ADMIN and STAFF roles.
    */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);// Scanner for user input

        // DAO objects to handle database operations
        UserDAO userDAO = new UserDAO();
        ProductDAO productDAO = new ProductDAO();
        StockDAO stockDAO = new StockDAO();
        SalesDAO salesDAO = new SalesDAO();

        System.out.println("===== ASCMS LOGIN =====");// Display login header

        System.out.print("Username: ");// Get username input
        String username = sc.nextLine();

        System.out.print("Password: ");// Get password input
        String password = sc.nextLine();

        String role = userDAO.login(username, password);// Attempt login and get user role (ADMIN / STAFF / null)

        if (role == null) { // If login fails
         System.out.println("Invalid Login!");
         } else { // If login is successful, display role and show appropriate menu
         System.out.println("Login Successful!");
         System.out.println("Role: " + role);

        // ADMIN MENU
        if (role.equalsIgnoreCase("ADMIN")) {

            while (true) {

                System.out.println("\n===== ADMIN MENU =====");// Display admin menu header
                System.out.println("1. Add Product");//Add product option for admin
                System.out.println("2. View Products");//View products option for admin
                System.out.println("3. Search Product");//Search product option for admin
                System.out.println("4. Update Product");//Update product option for admin
                System.out.println("5. Delete Product");//Delete product option for admin
                System.out.println("6. Stock In");//Stock in option for admin
                System.out.println("7. Sell Product");//Sell product option for admin
                System.out.println("8. Daily Sales Report");//Daly sales report option for admin
                System.out.println("9. Weekly Sales Report");//Weekly sales report option for admin
                System.out.println("10. Low Stock Alert");//Low stock alert option for admin
                System.out.println("11. Exit");//Exit option for admin

                System.out.print("Choose: ");// Get admin menu choice input
                int choice = sc.nextInt();

                switch (choice) { // Handles admin menu choices

                    case 1:
                        productDAO.addProduct();
                        break;

                    case 2:
                        productDAO.viewProducts();
                        break;

                    case 3:
                        productDAO.searchProduct();
                        break;

                    case 4:
                        productDAO.updateProduct();
                        break;

                    case 5:
                        productDAO.deleteProduct();
                        break;

                    case 6:
                        stockDAO.stockIn();
                        break;

                    case 7:
                        salesDAO.sellProduct();
                        break;

                    case 8:
                        salesDAO.dailySalesReport();
                        break;

                    case 9:
                        salesDAO.weeklySalesReport();
                        break;

                    case 10:
                        productDAO.lowStockAlert();
                        break;

                    case 11:
                        System.out.println("Exiting...");//
                        return;

                    default:
                        System.out.println("Invalid Choice!");// Print error message for invalid menu choice
                }
            }
        }

        // STAFF MENU
        else if (role.equalsIgnoreCase("STAFF")) {

            while (true) {
                    
                System.out.println("\n===== STAFF MENU =====");// Display staff menu header

                System.out.println("1. View Products");//View products option for staff
                System.out.println("2. Search Product");//Search product option for staff
                System.out.println("3. Sell Product");//Sell product option for staff
                System.out.println("4. Exit");//Exit option for staff

                System.out.print("Choose: ");// Get staff menu choice input
                int choice = sc.nextInt();

                switch (choice) {//Handles staff menu choices

                    case 1:
                        productDAO.viewProducts();
                        break;
                    case 2:
                        productDAO.searchProduct();
                        break;
                    case 3:
                        salesDAO.sellProduct();
                        break;
                    case 4:
                        System.out.println("Exiting...");// Exit message for staff
                        return;

                    default:
                        System.out.println("Invalid Choice!");// Print error message for invalid menu choice
                }
            }
        }
    }
    sc.close();// Closing scanner
    }
}