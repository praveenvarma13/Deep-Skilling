package com.example.mvc;

public class StudentView {
    public void printStudentDetails(String studentName, String studentId, String studentGrade) {
        System.out.println("--- Student Profile Dashboard ---");
        System.out.println("ID:    " + studentId);
        System.out.println("Name:  " + studentName);
        System.out.println("Grade: " + studentGrade);
        System.out.println("---------------------------------");
    }
}