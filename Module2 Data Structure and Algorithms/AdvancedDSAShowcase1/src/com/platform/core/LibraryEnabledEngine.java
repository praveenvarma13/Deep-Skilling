package com.platform.core;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Exercise 6: Extends Exercise 5 to add professional Library Catalog Search Capabilities.
 */
public class LibraryEnabledEngine extends UnifiedDynamicEngine {

    public LibraryEnabledEngine(int employeeCapacity) {
        super(employeeCapacity);
    }

    /**
     * Search Strategy A: Linear Search by Title
     * Time Complexity: O(n) - Works on completely unsorted book lists
     */
    public Book linearSearchByTitle(Book[] books, String targetTitle) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(targetTitle)) {
                return book;
            }
        }
        return null; // Not found
    }

    /**
     * Search Strategy B: Binary Search by Title
     * Time Complexity: O(log n) - Requires the book array to be sorted alphabetically by title
     */
    public Book binarySearchByTitle(Book[] books, String targetTitle) {
        // Enforce the Binary Search requirement: sort array by title strings
        Arrays.sort(books, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));

        int low = 0;
        int high = books.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = books[mid].getTitle().compareToIgnoreCase(targetTitle);

            if (comparison == 0) {
                return books[mid]; // Match found
            } else if (comparison < 0) {
                low = mid + 1;     // Eliminate the left half
            } else {
                high = mid - 1;    // Eliminate the right half
            }
        }
        return null; // Not found
    }

    public static void main(String[] args) {
        System.out.println("=== Running Step 6: Full Library Search Enabled Platform ===");
        LibraryEnabledEngine engine = new LibraryEnabledEngine(5);

        // 1. Structural Verification: Ensure prior linked task flows still operate flawlessly
        engine.addTask(new Task("T900", "Upgrade Library Network Firewalls", "In-Progress"));
        engine.traverseTasks();

        // 2. Setup sample catalog array datasets
        Book[] catalog = {
            new Book("B101", "Clean Code", "Robert C. Martin"),
            new Book("B102", "Introduction to Algorithms", "Thomas H. Cormen"),
            new Book("B103", "Design Patterns", "Erich Gamma"),
            new Book("B104", "Effective Java", "Joshua Bloch")
        };

        // 3. Test O(n) Linear Search on catalog
        System.out.println("--- Triggering Linear Search (Target: 'Design Patterns') ---");
        long start = System.nanoTime();
        Book matchA = engine.linearSearchByTitle(catalog, "Design Patterns");
        long end = System.nanoTime();
        System.out.println("Found Record: " + matchA);
        System.out.println("Linear Execution Duration: " + (end - start) + " ns\n");

        // 4. Test O(log n) Binary Search on catalog
        System.out.println("--- Triggering Binary Search (Target: 'Effective Java') ---");
        start = System.nanoTime();
        Book matchB = engine.binarySearchByTitle(catalog, "Effective Java");
        end = System.nanoTime();
        System.out.println("Found Record: " + matchB);
        System.out.println("Binary Execution Duration: " + (end - start) + " ns\n");
    }
}