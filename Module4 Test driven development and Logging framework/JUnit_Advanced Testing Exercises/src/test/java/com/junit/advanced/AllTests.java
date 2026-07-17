package com.junit.advanced;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Exercise 2: Test Suite bundling using exact JUnit 5 Specifications.
 */
@Suite // Pure JUnit 5 Suite Annotation
@SelectClasses({
    EvenCheckerTest.class,   // First test class to include
    SampleServiceTest.class,  // Second test class to include
    OrderedTests.class,
    ExceptionThrowerTest.class,
    PerformanceTesterTest.class
})
public class AllTests {
    // Remains intentionally empty. Acts as the entry engine for JUnit 5 Platform Suite Discovery.
}