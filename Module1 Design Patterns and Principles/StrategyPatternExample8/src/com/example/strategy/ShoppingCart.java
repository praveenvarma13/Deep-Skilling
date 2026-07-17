package com.example.strategy;

public class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    // Allows swapping the strategy dynamically at runtime
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(double amount) {
        if (paymentStrategy == null) {
            System.out.println("Please select a payment method before checking out.");
        } else {
            paymentStrategy.pay(amount);
        }
    }
}