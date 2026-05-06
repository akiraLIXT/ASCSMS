package com.ascsms;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        ProductDAO productDAO = new ProductDAO();
        StockDAO stockDAO = new StockDAO();
        SalesDAO salesDAO = new SalesDAO();

        System.out.println("===== ASCMS LOGIN =====");

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String role = userDAO.login(username, password);

        if (role == null) {

            System.out.println("Invalid Login!");
            return;
        }

        System.out.println("Login Successful!");
        System.out.println("Role: " + role);

        // ADMIN MENU
        if (role.equalsIgnoreCase("ADMIN")) {

            while (true) {

                System.out.println("\n===== ADMIN MENU =====");

                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Search Product");
                System.out.println("4. Update Product");
                System.out.println("5. Delete Product");
                System.out.println("6. Stock In");
                System.out.println("7. Sell Product");
                System.out.println("8. Daily Sales Report");
                System.out.println("9. Weekly Sales Report");
                System.out.println("10. Low Stock Alert");
                System.out.println("11. Exit");

                System.out.print("Choose: ");

                int choice = sc.nextInt();

                switch (choice) {

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
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        }

        // STAFF MENU
        else if (role.equalsIgnoreCase("STAFF")) {

            while (true) {

                System.out.println("\n===== STAFF MENU =====");

                System.out.println("1. View Products");
                System.out.println("2. Sell Product");
                System.out.println("3. Exit");

                System.out.print("Choose: ");

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        productDAO.viewProducts();
                        break;

                    case 2:
                        salesDAO.sellProduct();
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        }
    }
}