package com.example.adapter;

public class AdapterTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Adapter Pattern ---\n");

        // 1. Using PayPal through our unified system interface
        PayPalGateway payPalGateway = new PayPalGateway();
        PaymentProcessor paypalProcessor = new PayPalAdapter(payPalGateway);
        
        System.out.println("Client invoking PayPal adapter:");
        paypalProcessor.processPayment(150.00);

        System.out.println("\n----------------------------------\n");

        // 2. Using Stripe through our unified system interface
        StripeGateway stripeGateway = new StripeGateway();
        PaymentProcessor stripeProcessor = new StripeAdapter(stripeGateway);
        
        System.out.println("Client invoking Stripe adapter:");
        stripeProcessor.processPayment(275.50);
    }
}