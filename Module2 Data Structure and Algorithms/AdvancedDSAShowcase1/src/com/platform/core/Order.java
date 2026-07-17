package com.platform.core;

/**
 * Domain model representing a customer order for sorting and prioritization.
 */
public class Order {
    private final String orderId;
    private final String customerName;
    private final double totalPrice;

    public Order(String orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return String.format("[OrderID: %s | Customer: %s | Total: $%.2f]", 
                orderId, customerName, totalPrice);
    }
}