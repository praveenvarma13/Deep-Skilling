package com.junit.testing;

import java.util.Scanner;

/**
 * Exercise 2: Interactive Production Class
 */
public class Calculator {

    // Method 1: Adds two integers
    public int add(int a, int b) {
        System.out.println("   [Calculation Steps] Performing addition: " + a + " + " + b);
        return a + b;
    }

    // Method 2: Multiplies two integers
    public int multiply(int a, int b) {
        System.out.println("   [Calculation Steps] Performing multiplication: " + a + " * " + b);
        return a * b;
    }

    // The main method allows you to give custom inputs interactively
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();
        
        System.out.println("=== 🧮 Interactive Calculator Engine ===");
        
        // 1. Get user input for Addition
        System.out.print("Enter first number to add: ");
        int num1 = scanner.nextInt();
        System.out.print("Enter second number to add: ");
        int num2 = scanner.nextInt();
        
        int addResult = calc.add(num1, num2);
        System.out.println("➡️ Final Result of Addition: " + addResult + "\n");

        // 2. Get user input for Multiplication
        System.out.print("Enter first number to multiply: ");
        int num3 = scanner.nextInt();
        System.out.print("Enter second number to multiply: ");
        int num4 = scanner.nextInt();
        
        int multiplyResult = calc.multiply(num3, num4);
        System.out.println("➡️ Final Result of Multiplication: " + multiplyResult);
        
        System.out.println("=========================================");
        scanner.close();
    }
}