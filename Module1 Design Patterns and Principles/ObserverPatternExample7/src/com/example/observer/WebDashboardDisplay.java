package com.example.observer;

public class WebDashboardDisplay implements Observer {
    @Override
    public void update(double stockPrice) {
        System.out.println("-> [Web Dashboard Widget]: Refreshing graph with current value: $" + stockPrice);
    }
}