package com.example.adapter;

public class StripeGateway {
    public void charge(double amountInDouble) {
        System.out.println("Charging credit card with amount: $" + amountInDouble + " via Stripe API.");
    }
}