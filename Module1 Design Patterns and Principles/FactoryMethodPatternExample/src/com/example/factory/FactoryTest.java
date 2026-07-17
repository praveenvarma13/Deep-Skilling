package com.example.factory;

public class FactoryTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Factory Method Pattern ---\n");

        // 1. Client wants to work with a Word Document
        DocumentFactory wordFactory = new WordFactory();
        System.out.println("Client requests a Word Document:");
        wordFactory.manageDocument(); 
        
        System.out.println("\n----------------------------------\n");

        // 2. Client wants to work with a PDF Document
        DocumentFactory pdfFactory = new PdfFactory();
        System.out.println("Client requests a PDF Document:");
        pdfFactory.manageDocument();

        System.out.println("\n----------------------------------\n");

        // 3. Client wants to work with an Excel Document
        DocumentFactory excelFactory = new ExcelFactory();
        System.out.println("Client requests an Excel Document:");
        excelFactory.manageDocument();
    }
}