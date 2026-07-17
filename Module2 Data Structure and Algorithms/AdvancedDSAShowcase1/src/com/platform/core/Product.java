package com.platform.core;

/**
 * Domain model representing a product within the enterprise system.
 */
public class Product {
    private final String productId;
    private String productName;
    private int quantity;
    private double price;
    private String category; // Added early to ensure seamless compatibility with Exercise 2 search requirements

    public Product(String productId, String productName, int quantity, double price, String category) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return String.format("[ID: %s | Name: %s | Cat: %s | Qty: %d | Price: $%.2f]", 
                productId, productName, category, quantity, price);
    }
}