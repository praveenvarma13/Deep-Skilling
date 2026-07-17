package com.example.factory;

public class ExcelDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening Excel Spreadsheet (.xlsx)...");
    }

    @Override
    public void close() {
        System.out.println("Saving formulas and closing Excel Spreadsheet.");
    }
}