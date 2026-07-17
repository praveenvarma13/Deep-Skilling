package com.platform.core;

/**
 * Exercise 3: Extends Exercise 2 to layer order prioritization via Bubble Sort and Quick Sort.
 */
public class SortedPlatformEngine extends SearchEnabledInventory {

    /**
     * Algorithm 1: Bubble Sort by Price (Descending)
     * Time Complexity: O(n^2) - For demonstration purposes
     */
    public void bubbleSortOrders(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Swap if the current order is less than the next order (High-to-Low)
                if (orders[j].getTotalPrice() < orders[j + 1].getTotalPrice()) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Algorithm 2: Quick Sort Entry Point
     * Time Complexity: O(n log n) average - Production Grade
     */
    public void quickSortOrders(Order[] orders, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high);
            quickSortOrders(orders, low, pivotIndex - 1);  // Sort left partition
            quickSortOrders(orders, pivotIndex + 1, high); // Sort right partition
        }
    }

    private int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // Sort Descending (High-to-Low prioritization)
            if (orders[j].getTotalPrice() >= pivot) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;

        return i + 1;
    }

    public static void main(String[] args) {
        System.out.println("=== Running Step 3: Combined Inventory, Search & Sort Engine ===");
        SortedPlatformEngine engine = new SortedPlatformEngine();

        // 1. Verify prior exercise structural compliance (Inventory Data Preservation)
        engine.addProduct(new Product("P001", "MacBook Pro M3", 10, 1999.99, "Electronics"));
        System.out.println("Verification - Binary Search Looked Up Item: " + engine.binarySearchById("P001") + "\n");

        // 2. Initialize sample customer orders
        Order[] batchA = {
            new Order("O101", "Alice Jenkins", 250.50),
            new Order("O102", "Bob Smith", 1299.99),
            new Order("O103", "Charlie Brown", 45.00),
            new Order("O104", "Diana Prince", 899.00)
        };

        Order[] batchB = batchA.clone(); // Clone for a fair benchmark comparison

        // 3. Execution of Bubble Sort
        System.out.println("--- Executing Bubble Sort O(n^2) ---");
        long start = System.nanoTime();
        engine.bubbleSortOrders(batchA);
        long end = System.nanoTime();
        for (Order o : batchA) System.out.println(o);
        System.out.println("Bubble Sort Runtime: " + (end - start) + " ns\n");

        // 4. Execution of Quick Sort
        System.out.println("--- Executing Quick Sort O(n log n) ---");
        start = System.nanoTime();
        engine.quickSortOrders(batchB, 0, batchB.length - 1);
        end = System.nanoTime();
        for (Order o : batchB) System.out.println(o);
        System.out.println("Quick Sort Runtime: " + (end - start) + " ns\n");
    }
}