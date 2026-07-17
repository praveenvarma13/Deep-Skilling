package com.example.proxy;

public class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromRemoteServer();
    }

    private void loadFromRemoteServer() {
        System.out.println("Connecting to remote server... Downloading " + fileName);
        try {
            Thread.sleep(1500); // Simulate network latency
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Download complete: " + fileName + " is ready.");
    }

    @Override
    public void display() {
        System.out.println("Rendering and displaying: " + fileName);
    }
}