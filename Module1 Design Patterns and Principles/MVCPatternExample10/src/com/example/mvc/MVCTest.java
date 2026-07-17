package com.example.mvc;

public class MVCTest {
    public static void main(String[] args) {
        System.out.println("--- Testing MVC Architecture Pattern ---\n");

        // 1. Fetch initial student record from data layer/db mock
        Student model = retrieveStudentFromDatabase();

        // 2. Create the View to render the data
        StudentView view = new StudentView();

        // 3. Initialize Controller to wire the components up
        StudentController controller = new StudentController(model, view);

        // Render current initial view layout
        System.out.println("Displaying initial state:");
        controller.updateView();

        // 4. Update data via Controller actions
        System.out.println("\n[Action]: Updating grade and student name via controller...");
        controller.setStudentName("Alex Smith");
        controller.setStudentGrade("A+");

        // Render refreshed view layout matching updated model
        System.out.println("Displaying updated state:");
        controller.updateView();
    }

    private static Student retrieveStudentFromDatabase() {
        Student student = new Student();
        student.setName("John Doe");
        student.setId("2026-STU-0892");
        student.setGrade("B");
        return student;
    }
}