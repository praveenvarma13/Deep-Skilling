package com.platform.core;

/**
 * Exercise 5: Extends Exercise 4 to incorporate a custom Dynamic Singly Linked List engine layer.
 */
public class UnifiedDynamicEngine extends CompleteEnterpriseEngine {

    private Task head; // The entry point of our linked list

    public UnifiedDynamicEngine(int employeeCapacity) {
        super(employeeCapacity);
        this.head = null;
    }

    /**
     * Operation 1: Add Task to List (Appends at the tail end)
     * Time Complexity: O(n) without tail pointer reference
     */
    public void addTask(Task newTask) {
        if (newTask == null) return;
        
        if (head == null) {
            head = newTask;
        } else {
            Task current = head;
            while (current.next != null) {
                current = current.next; // Traverse to the end
            }
            current.next = newTask;
        }
        System.out.println("[TASK PIPELINE] Appended Task: " + newTask.getTaskName());
    }

    /**
     * Operation 2: Search Task by ID
     * Time Complexity: O(n) linear scan
     */
    public Task searchTask(String taskId) {
        Task current = head;
        while (current != null) {
            if (current.getTaskId().equalsIgnoreCase(taskId)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Operation 3: Delete Task by ID
     * Time Complexity: O(n) to find, but O(1) pointer re-linking
     */
    public boolean deleteTask(String taskId) {
        if (head == null) {
            System.out.println("[LINKED LIST ERROR] Pipeline empty.");
            return false;
        }

        // Case A: The item to remove is the head node
        if (head.getTaskId().equalsIgnoreCase(taskId)) {
            System.out.println("[TASK PIPELINE] Removed Head Task: " + head.getTaskName());
            head = head.next; // Move head pointer forward
            return true;
        }

        // Case B: Traverse to locate the target node while keeping track of the previous node
        Task current = head;
        Task previous = null;
        
        while (current != null && !current.getTaskId().equalsIgnoreCase(taskId)) {
            previous = current;
            current = current.next;
        }

        if (current == null) {
            System.out.println("[LINKED LIST ERROR] Task ID not found: " + taskId);
            return false;
        }

        // Disconnect the node by re-linking the previous node around it
        System.out.println("[TASK PIPELINE] Removed Task: " + current.getTaskName());
        previous.next = current.next;
        return true;
    }

    /**
     * Operation 4: Traverse and Print
     * Time Complexity: O(n)
     */
    public void traverseTasks() {
        System.out.println("\n--- Current Dynamic Task LinkedList Status ---");
        if (head == null) {
            System.out.println("No active corporate tasks in pipeline.");
        } else {
            Task current = head;
            while (current != null) {
                System.out.println(current);
                current = current.next;
            }
        }
        System.out.println("----------------------------------------------\n");
    }

    public static void main(String[] args) {
        System.out.println("=== Running Step 5: Advanced Linked-List Platform Layer ===");
        // Instantiate engine with 5 spots for employees (inherited from Exercise 4)
        UnifiedDynamicEngine engine = new UnifiedDynamicEngine(5);

        // 1. Verify prior exercise structural continuity (Data Check)
        engine.addProduct(new Product("P002", "Mechanical Keyboard", 10, 150.0, "Accessories"));
        System.out.println("Verification - Inherited Map Looked Up Stock: " + engine.binarySearchById("P002") + "\n");

        // 2. Test Linked List Insertion Operations
        engine.addTask(new Task("T101", "Configure Web Load Balancers", "In-Progress"));
        engine.addTask(new Task("T102", "Audit Database Index Fragmentation", "Pending"));
        engine.addTask(new Task("T103", "Review Candidate Git Repositories", "Completed"));

        engine.traverseTasks();

        // 3. Test Linked List Search Operation
        System.out.println("Searching for Task T102...");
        Task foundTask = engine.searchTask("T102");
        System.out.println("Search Result Match: " + foundTask + "\n");

        // 4. Test Linked List Deletion Operation (No structural shifting required!)
        engine.deleteTask("T102");

        // View final chain output to confirm the pointers cleanly patched together
        engine.traverseTasks();
    }
}