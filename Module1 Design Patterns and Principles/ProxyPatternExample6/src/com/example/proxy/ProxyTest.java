package com.example.proxy;

public class ProxyTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Proxy Pattern ---\n");

        Image image = new ProxyImage("high_res_space_nebula.png");

        // First display call: must download the image
        System.out.println("--- First Call to display() ---");
        image.display();
        
        System.out.println("\n----------------------------------\n");

        // Second display call: should skip network download entirely and pull from cache
        System.out.println("--- Second Call to display() ---");
        image.display();
    }
}