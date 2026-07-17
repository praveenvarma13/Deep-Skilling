package com.library.repository;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private final List<String> books = new ArrayList<>();

    public BookRepository() {
        // Seed initial mock data
        books.add("The Great Gatsby");
        books.add("1984");
        books.add("To Kill a Mockingbird");
    }

    public List<String> findAllBooks() {
        return books;
    }

    public void addBook(String title) {
        books.add(title);
    }
}