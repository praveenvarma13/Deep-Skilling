package com.example.observer;

public class ObserverTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Observer Pattern ---");

        // Create a subject tracking Apple stock
        StockMarket appleStock = new StockMarket("AAPL", 150.00);

        // Create Observers
        Observer mobileClient = new MobileAppDisplay("Alice");
        Observer webClient = new WebDashboardDisplay();

        // Register subscribers
        appleStock.registerObserver(mobileClient);
        appleStock.registerObserver(webClient);

        // First price spike impacts everyone attached
        appleStock.setStockPrice(155.25);

        // Unsubscribe one client
        System.out.println("\n-- Alice disconnects her mobile app alerts --");
        appleStock.removeObserver(mobileClient);

        // Next price update should only notify the web dashboard
        appleStock.setStockPrice(158.00);
    }
}