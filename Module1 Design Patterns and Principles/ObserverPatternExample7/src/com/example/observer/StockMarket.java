package com.example.observer;

import java.util.ArrayList;
import java.util.List;

public class StockMarket implements Subject {
    private List<Observer> observers;
    private String stockSymbol;
    private double stockPrice;

    public StockMarket(String stockSymbol, double initialPrice) {
        this.observers = new ArrayList<>();
        this.stockSymbol = stockSymbol;
        this.stockPrice = initialPrice;
    }

    public void setStockPrice(double newPrice) {
        System.out.println("\n[Market Update]: " + stockSymbol + " price changed to $" + newPrice);
        this.stockPrice = newPrice;
        notifyObservers(); // Trigger notifications immediately
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockPrice);
        }
    }
}