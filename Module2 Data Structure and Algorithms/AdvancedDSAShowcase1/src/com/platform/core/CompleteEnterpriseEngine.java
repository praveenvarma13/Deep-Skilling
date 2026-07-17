package com.platform.core;

/**
 * Exercise 4: Extends Exercise 3 to layer Contiguous Memory Array operations for Employee Management.
 */
public class CompleteEnterpriseEngine extends SortedPlatformEngine {
    
    private final Employee[] employeeTable;
    private int employeeCount;

    // Constructor defining fixed capacity constraint for array simulation
    public CompleteEnterpriseEngine(int capacity) {
        this.employeeTable = new Employee[capacity];
        this.employeeCount = 0;
    }

    /**
     * Operation 1: Add Employee
     * Time Complexity: O(1) if capacity remains available
     */
    public boolean addEmployee(Employee emp) {
        if (employeeCount >= employeeTable.length) {
            System.out.println("[ARRAY ERROR] Roster full. Cannot insert employee.");
            return false;
        }
        employeeTable[employeeCount] = emp;
        employeeCount++;
        System.out.println("[ROSTER] Registered Employee: " + emp.getName());
        return true;
    }

    /**
     * Operation 2: Search Employee by ID
     * Time Complexity: O(n) linear lookup sweep
     */
    public Employee searchEmployee(String empId) {
        for (int i = 0; i < employeeCount; i++) {
            if (employeeTable[i].getEmployeeId().equalsIgnoreCase(empId)) {
                return employeeTable[i];
            }
        }
        return null;
    }

    /**
     * Operation 3: Delete Employee by ID
     * Time Complexity: O(n) - Requires shifting adjacent memory indices
     */
    public boolean deleteEmployee(String empId) {
        int targetIndex = -1;
        
        // Find the index location
        for (int i = 0; i < employeeCount; i++) {
            if (employeeTable[i].getEmployeeId().equalsIgnoreCase(empId)) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex == -1) {
            System.out.println("[ARRAY ERROR] Deletion target ID not found: " + empId);
            return false;
        }

        // Shift remaining items left to close the memory gap sequentially
        String targetName = employeeTable[targetIndex].getName();
        for (int i = targetIndex; i < employeeCount - 1; i++) {
            employeeTable[i] = employeeTable[i + 1];
        }
        
        employeeTable[employeeCount - 1] = null; // Clear duplicate dangling reference
        employeeCount--;
        System.out.println("[ROSTER] Dropped Employee record: " + targetName);
        return true;
    }

    /**
     * Operation 4: Traverse and Print
     * Time Complexity: O(n) linear footprint layout sweep
     */
    public void traverseEmployees() {
        System.out.println("\n--- Current Contiguous Roster Roll ---");
        if (employeeCount == 0) {
            System.out.println("No employees registered.");
        } else {
            for (int i = 0; i < employeeCount; i++) {
                System.out.println(employeeTable[i]);
            }
        }
        System.out.println("--------------------------------------\n");
    }

    public static void main(String[] args) {
        System.out.println("=== Running Step 4: Full Multi-Domain Platform Engine ===");
        // Instantiate engine with max capacity constraint of 5 slots
        CompleteEnterpriseEngine engine = new CompleteEnterpriseEngine(5);

        // 1. Verify prior data layers (Preserving Inventory mapping from Exercise 1)
        engine.addProduct(new Product("P99", "Enterprise Router Core", 5, 4500.0, "Networking"));
        System.out.println("Verification - Inherited Map Looked Up Stock: " + engine.linearSearchByName("Enterprise Router Core") + "\n");

        // 2. Test O(1) Add Operations
        engine.addEmployee(new Employee("E001", "Alice Vance", "Chief Architect", 165000.0));
        engine.addEmployee(new Employee("E002", "Gordon Freeman", "Research Associate", 95000.0));
        engine.addEmployee(new Employee("E003", "Barney Calhoun", "Security Lead", 75000.0));
        
        engine.traverseEmployees();

        // 3. Test O(n) Search Operation
        System.out.println("Searching for E002...");
        Employee match = engine.searchEmployee("E002");
        System.out.println("Search Result Found Match: " + match + "\n");

        // 4. Test O(n) Delete Operation (Forces element shift)
        engine.deleteEmployee("E002");
        
        // Render updated status showing sequential order remains unbroken
        engine.traverseEmployees();
    }
}