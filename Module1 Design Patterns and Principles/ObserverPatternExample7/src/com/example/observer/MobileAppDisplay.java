package com.example.observer;

public class MobileAppDisplay implements Observer {
    private String userName;

    public MobileAppDisplay(String userName) {
        this.userName = userName;
    }

    @Override
    public void update(double stockPrice) {
        System.out.println("-> Push Notification to [" + userName + "]: Stock price is now $" + stockPrice);
    }
}