package com.platform.core;

/**
 * Domain model representing a library catalog book item.
 */
public class Book {
    private final String bookId;
    private final String title;
    private final String author;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return String.format("[BookID: %s | Title: '%s' | Author: %s]", bookId, title, author);
    }
}