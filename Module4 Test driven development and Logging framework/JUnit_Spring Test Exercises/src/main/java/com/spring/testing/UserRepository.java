package com.spring.testing;

import java.util.List;
import java.util.Optional;

/**
 * Exercise 7: Extended Data Repository Interface with Custom Query capability.
 */
public interface UserRepository {
    Optional<User> findById(Long id);
    
    // ADD THIS METHOD FROM YOUR MANUAL:
    List<User> findByName(String name); //
}