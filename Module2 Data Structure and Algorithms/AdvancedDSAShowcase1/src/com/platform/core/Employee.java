package com.platform.core;

/**
 * Domain model representing a company employee record.
 */
public class Employee {
    private final String employeeId;
    private final String name;
    private final String position;
    private final double salary;

    public Employee(String employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return String.format("[EmpID: %s | Name: %s | Role: %s | Salary: $%.2f]", 
                employeeId, name, position, salary);
    }
}