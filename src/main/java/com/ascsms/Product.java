package com.ascsms;

    /**
    * Product class represents a single product in the canteen system.
    * This is a model (POJO) used to store and transfer product data.
    */

public class Product {

    private int id; // Unique ID of the product
    private String name; // Name of the product (e.g., Burger, Juice)
    private double price; // Price per unit of the product
    private int quantity; // Available quantity in stock

    /**
     * Parameterized constructor
     * Used when creating an empty Product object
     */
    public Product() {}

    /**
     * Parameterized constructor
     * Used when creating a Product object with specific values
     */
    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters for id, name, price, and quantity
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
