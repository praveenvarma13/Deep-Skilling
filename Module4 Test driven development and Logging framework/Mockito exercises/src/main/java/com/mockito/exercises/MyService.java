package com.mockito.exercises;

/**
 * Exercise 1, 2 & 3: Service layer routing argument payloads to the API.
 */
public class MyService {
    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchData() {
        return externalApi.getData();
    }

    // ADD THIS METHOD FOR EXERCISE 3:
    public String processQuery(String query) {
        System.out.println("🔍 [Service Layer] Processing query parameter: " + query);
        return externalApi.formatData(query);
    }
}