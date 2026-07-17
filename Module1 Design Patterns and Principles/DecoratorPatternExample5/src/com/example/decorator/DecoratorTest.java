package com.example.decorator;

public class DecoratorTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Decorator Pattern ---\n");

        // 1. Just a basic Email notifier
        System.out.println("Scenario A: Standard Base Notifier Only");
        Notifier standardEmail = new EmailNotifier();
        standardEmail.send("System maintenance scheduled at 12:00 AM.");

        System.out.println("\n----------------------------------\n");

        // 2. Wrap the email with SMS functionality
        System.out.println("Scenario B: Email + SMS Notification");
        Notifier emailAndSms = new SMSNotifierDecorator(new EmailNotifier());
        emailAndSms.send("Critical Security Alert!");

        System.out.println("\n----------------------------------\n");

        // 3. Stacking multiple layers: Email + SMS + Slack
        System.out.println("Scenario C: Full Channel Pack (Email + SMS + Slack)");
        Notifier allChannels = new SlackNotifierDecorator(
                                    new SMSNotifierDecorator(
                                        new EmailNotifier()
                                    )
                               );
        allChannels.send("Server down! Deployment failed!");
    }
}