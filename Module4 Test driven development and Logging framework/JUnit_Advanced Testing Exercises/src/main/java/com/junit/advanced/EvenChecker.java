package com.junit.advanced;

import java.util.Scanner;

public class EvenChecker {
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EvenChecker checker = new EvenChecker();
        
        System.out.println("=========================================");
        System.out.println("  🔢 JUNIT 5 LAB MODULE: EVEN CHECKER   ");
        System.out.println("=========================================");
        System.out.println("Type any integer below to verify its status dynamically.");
        System.out.println("Type -999 to shut down the application.\n");

        while (true) {
            System.out.print("👉 Enter an integer input: ");
            int inputNumber = scanner.nextInt();
            if (inputNumber == -999) break;

            boolean result = checker.isEven(inputNumber);
            System.out.println("➡️  Result: The number [ " + inputNumber + " ] is " + (result ? "EVEN" : "ODD") + "\n");
        }
        scanner.close();
    }
}