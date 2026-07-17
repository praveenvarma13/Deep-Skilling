package com.platform.core;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Exercise 2: Extends Exercise 1 baseline to provide optimized linear and binary search algorithms.
 */
public class SearchEnabledInventory extends InventoryEngine {

    /**
     * Algorithm 1: Linear Search by Product Name
     * Time Complexity: O(n) - Best for unsorted datasets
     */
    public Product linearSearchByName(String targetName) {
        // Convert internal map values to an array for simulation
        Product[] products = productMap.values().toArray(new Product[0]);
        
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(targetName)) {
                return product; // Found item
            }
        }
        return null; // Not found
    }

    /**
     * Algorithm 2: Binary Search by Product ID
     * Time Complexity: O(log n) - Requires an array explicitly sorted by ID
     */
    public Product binarySearchById(String targetId) {
        // 1. Extract map items into a raw array
        Product[] products = productMap.values().toArray(new Product[0]);
        
        // 2. Binary search REQUIREMENT: The array MUST be sorted by the key we are searching for
        Arrays.sort(products, Comparator.comparing(Product::getProductId));
        
        int low = 0;
        int high = products.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2; // Prevents potential integer overflow
            int comparison = products[mid].getProductId().compareTo(targetId);
            
            if (comparison == 0) {
                return products[mid]; // Found item
            } else if (comparison < 0) {
                low = mid + 1; // Search the right half
            } else {
                high = mid - 1; // Search the left half
            }
        }
        return null; // Not found
    }

    // Integrated Test Runner for Exercise 2 combining prior setup data state
    public static void main(String[] args) {
        System.out.println("=== Running Step 2: Search-Optimized Platform Engine ===");
        SearchEnabledInventory searchEngine = new SearchEnabledInventory();

        // 1. Inherited setup operations from Exercise 1 data baseline population
        searchEngine.addProduct(new Product("P005", "Sony WH-1000XM5", 40, 399.99, "Audio"));
        searchEngine.addProduct(new Product("P001", "MacBook Pro M3", 15, 1999.99, "Electronics"));
        searchEngine.addProduct(new Product("P004", "iPad Pro 12.9", 25, 1099.00, "Electronics"));
        searchEngine.addProduct(new Product("P002", "Mechanical Keyboard", 80, 120.50, "Accessories"));

        searchEngine.displayAll();

        // 2. Testing Linear Search O(n)
        System.out.println("--- Triggering Linear Search (Target Name: 'Mechanical Keyboard') ---");
        long startTime = System.nanoTime();
        Product foundLinear = searchEngine.linearSearchByName("Mechanical Keyboard");
        long endTime = System.nanoTime();
        System.out.println("Result: " + foundLinear);
        System.out.println("Execution Time: " + (endTime - startTime) + " ns\n");

        // 3. Testing Binary Search O(log n)
        System.out.println("--- Triggering Binary Search (Target ID: 'P004') ---");
        startTime = System.nanoTime();
        Product foundBinary = searchEngine.binarySearchById("P004");
        endTime = System.nanoTime();
        System.out.println("Result: " + foundBinary);
        System.out.println("Execution Time: " + (endTime - startTime) + " ns\n");
    }
}