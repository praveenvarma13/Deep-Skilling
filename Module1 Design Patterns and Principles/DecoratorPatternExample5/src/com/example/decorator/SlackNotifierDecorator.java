package com.example.decorator;

public class SlackNotifierDecorator extends NotifierDecorator {
    public SlackNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message); // Execute core behavior
        sendSlackMessage(message); // Execute additional dynamic behavior
    }

    private void sendSlackMessage(String message) {
        System.out.println("Sending Slack Channel Message: " + message);
    }
}