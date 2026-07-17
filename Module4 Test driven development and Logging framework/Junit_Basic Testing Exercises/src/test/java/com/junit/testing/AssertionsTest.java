package com.junit.testing;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Exercise 3: Dynamic Calculation Input & Comparison Test Suite
 */
@RunWith(Parameterized.class)
public class AssertionsTest {

    private int firstNumber;
    private int secondNumber;
    private int expectedSum;

    // The constructor maps your values automatically during execution
    public AssertionsTest(int firstNumber, int secondNumber, int expectedSum) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.expectedSum = expectedSum;
    }

    /**
     * 📝 YOUR CUSTOM DATA INPUT TABLE
     * You can edit the numbers inside this block freely!
     * Format: { firstNumber, secondNumber, expectedSum }
     */
    @Parameterized.Parameters
    public static Collection<Object[]> myCustomInputs() {
        return Arrays.asList(new Object[][] {
            { 10, 20, 30 },  // Scenario 1: 10 + 20 should equal 30 (Passes)
            { 50, 50, 100 }, // Scenario 2: 50 + 50 should equal 100 (Passes)
            {  6,  6,  12 }  // Scenario 3: 5 + 5 should equal 12 (This will intentionally fail to show you how a failure looks!)
        });
    }

    @Test
    public void compareCalculatedSum() {
        System.out.println("🧪 [Testing Inputs] First: " + firstNumber + " | Second: " + secondNumber);
        
        // Step 1: Calculate the actual sum dynamically
        int actualSum = firstNumber + secondNumber;
        System.out.println("🧮 [Calculating] " + firstNumber + " + " + secondNumber + " = " + actualSum);
        
        // Step 2: Compare the actual sum against your expected input number
        System.out.println("🔍 [Comparing] Is Actual (" + actualSum + ") equal to your Expected (" + expectedSum + ")?");
        
        assertEquals("❌ The calculated sum does not match your expected target number!", expectedSum, actualSum);
        
        System.out.println("✔️ Success! The comparison matches perfectly.\n");
    }
}