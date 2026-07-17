package com.example.di;

public class DITest {
    public static void main(String[] args) {
        System.out.println("--- Testing Dependency Injection Pattern ---\n");

        String msg = "Hi User, your transaction was successful!";
        String email = "customer@example.com";

        // 1. Create the dependency component
        MessageService service = new EmailServiceImpl();

        // 2. Inject the dependency into the client application container via constructor
        MyApplication app = new MyApplication(service);

        // 3. Run the business logic
        app.processMessages(msg, email);
    }
}