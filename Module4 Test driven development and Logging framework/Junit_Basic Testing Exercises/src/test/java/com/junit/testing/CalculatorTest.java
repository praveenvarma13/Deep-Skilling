package com.junit.testing;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Exercise 2: JUnit test suite with descriptive tracking logs.
 */
public class CalculatorTest {

    @Test
    public void testAdd() {
        Calculator calc = new Calculator();
        
        int inputA = 25;
        int inputB = 15;
        int expectedResult = 40;
        
        System.out.println("🧪 [JUnit Running] Testing 'add' method with inputs: " + inputA + ", " + inputB);
        int actualResult = calc.add(inputA, inputB);
        
        System.out.println("🔍 [JUnit Verifying] Expected: " + expectedResult + " | Actual Result: " + actualResult);
        assertEquals(expectedResult, actualResult);
        System.out.println("✔️ Success! Verification passed.\n");
    }

    @Test
    public void testMultiply() {
        Calculator calc = new Calculator();
        
        int inputA = 6;
        int inputB = 7;
        int expectedResult = 42;
        
        System.out.println("🧪 [JUnit Running] Testing 'multiply' method with inputs: " + inputA + ", " + inputB);
        int actualResult = calc.multiply(inputA, inputB);
        
        System.out.println("🔍 [JUnit Verifying] Expected: " + expectedResult + " | Actual Result: " + actualResult);
        assertEquals(expectedResult, actualResult);
        System.out.println("✔️ Success! Verification passed.\n");
    }
}