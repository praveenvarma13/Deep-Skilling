package com.logging.exercises;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 

/**
 * Exercise 1: Core SLF4J logging demo for ERROR and WARN thresholds.
 */
public class LoggingExample { 
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class); 
 
    public static void main(String[] args) { 
        // Triggering standard logging states
        logger.error("This is an error message"); 
        logger.warn("This is a warning message"); 
    } 

    // Helper methods for our interactive dashboard context
    public void logCustomError(String message) {
        logger.error(message);
    }

    public void logCustomWarning(String message) {
        logger.warn(message);
    }
}