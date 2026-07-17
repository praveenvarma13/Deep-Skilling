package com.spring.testing;

/**
 * Exercise 2: User Entity Model.
 */
public class User {
    private Long id;
    private String name;

    // Default constructor
    public User() {}

    // Convenience constructor
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}