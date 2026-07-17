package com.spring.testing;

import org.springframework.stereotype.Service;

/**
 * Exercise 2, 5 & 6: Service layer managing user search engine logic rules.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // UPDATED FOR EXERCISE 6: Throw custom exception if user is missing
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found in the repository system.")); //
    }

    public User saveUser(User user) {
        System.out.println("💾 [Service Layer] Processing and saving user payload: " + user.getName());
        return user;
    }
}