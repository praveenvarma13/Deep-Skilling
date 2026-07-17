package com.mockito.exercises;

/**
 * Exercise 1: External API client system definition contract[cite: 4, 16].
 */
public interface ExternalApi {
    String getData(); //
    String formatData(String input);
}