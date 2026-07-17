package com.example.factory;

public class PdfDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening PDF Document (.pdf) in Adobe Reader...");
    }

    @Override
    public void close() {
        System.out.println("Closing PDF Document.");
    }
}