package com.example.decorator;

public class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message); // Execute core behavior
        sendSMS(message);    // Execute additional dynamic behavior
    }

    private void sendSMS(String message) {
        System.out.println("Sending SMS Notification: " + message);
    }
}