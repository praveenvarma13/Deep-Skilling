package com.example.di;

public class MyApplication {
    private final MessageService service;

    // Dependency is injected via the constructor
    public MyApplication(MessageService service) {
        this.service = service;
    }

    public void processMessages(String msg, String rec) {
        // Delegate responsibility to the injected service
        this.service.sendMessage(msg, rec);
    }
}