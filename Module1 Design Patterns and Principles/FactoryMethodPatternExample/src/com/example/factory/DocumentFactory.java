package com.example.factory;

public abstract class DocumentFactory {
    // This is the core Factory Method
    public abstract Document createDocument();

    // Helper business method that operates on the created object
    public void manageDocument() {
        Document doc = createDocument();
        doc.open();
        doc.close();
    }
}