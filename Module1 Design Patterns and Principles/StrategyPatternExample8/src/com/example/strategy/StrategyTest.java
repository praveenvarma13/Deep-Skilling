package com.example.strategy;

public class StrategyTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Strategy Pattern ---\n");

        ShoppingCart cart = new ShoppingCart();

        // 1. Pay using Credit Card
        System.out.println("Scenario A: Paying via Credit Card");
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9876-5432", "John Doe"));
        cart.checkout(250.75);

        System.out.println("\n----------------------------------\n");

        // 2. Switch payment method dynamically to PayPal
        System.out.println("Scenario B: Switching to PayPal at runtime");
        cart.setPaymentStrategy(new PayPalPayment("john.doe@example.com"));
        cart.checkout(89.99);
    }
}