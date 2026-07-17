package com.platform.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Core warehouse inventory engine utilizing HashMap for O(1) operations.
 */
public class InventoryEngine {
    // Protected so subsequent exercise features can access it cleanly
    protected final Map<String, Product> productMap = new HashMap<>();

    /**
     * Adds a product to the inventory.
     * Time Complexity: O(1) average
     */
    public void addProduct(Product product) {
        if (product == null) return;
        productMap.put(product.getProductId(), product);
        System.out.println("[CORE] Added Product: " + product.getProductName());
    }

    /**
     * Updates an existing product's attributes.
     * Time Complexity: O(1) average
     */
    public void updateProduct(String productId, String name, int qty, double price) {
        Product product = productMap.get(productId);
        if (product != null) {
            product.setProductName(name);
            product.setQuantity(qty);
            product.setPrice(price);
            System.out.println("[CORE] Updated Product: " + productId);
        } else {
            System.out.println("[ERROR] Product not found for update: " + productId);
        }
    }

    /**
     * Deletes a product from inventory records.
     * Time Complexity: O(1) average
     */
    public void deleteProduct(String productId) {
        if (productMap.containsKey(productId)) {
            Product removed = productMap.remove(productId);
            System.out.println("[CORE] Deleted Product: " + removed.getProductName());
        } else {
            System.out.println("[ERROR] Product not found for deletion: " + productId);
        }
    }

    /**
     * Utility method to print all products.
     */
    public void displayAll() {
        System.out.println("\n--- Current Inventory Status ---");
        productMap.values().forEach(System.out::println);
        System.out.println("--------------------------------\n");
    }

    // Isolated test runner for Exercise 1
    public static void main(String[] args) {
        System.out.println("=== Running Step 1: Inventory Core Baseline ===");
        InventoryEngine engine = new InventoryEngine();

        // Add items
        engine.addProduct(new Product("P001", "MacBook Pro M3", 15, 1999.99, "Electronics"));
        engine.addProduct(new Product("P002", "Mechanical Keyboard", 80, 120.50, "Accessories"));
        engine.addProduct(new Product("P003", "Wireless Gaming Mouse", 120, 89.99, "Accessories"));
        
        engine.displayAll();

        // Update item
        engine.updateProduct("P002", "Mechanical Keyboard (RGB V2)", 75, 135.00);
        
        // Delete item
        engine.deleteProduct("P003");

        engine.displayAll();
    }
}