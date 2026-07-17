package com.example.proxy;

public class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
        // Notice: The RealImage is NOT instantiated here! (Lazy Initialization)
    }

    @Override
    public void display() {
        if (realImage == null) {
            System.out.println("[Proxy] Image not loaded yet. Initializing target RealImage...");
            realImage = new RealImage(fileName); // Caching the object after first load
        } else {
            System.out.println("[Proxy] Retrieving " + fileName + " from cache.");
        }
        realImage.display();
    }
}