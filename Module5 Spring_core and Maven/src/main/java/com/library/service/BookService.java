package com.library.service;

import com.library.repository.BookRepository;
import com.library.repository.LibraryConfiguration;
import java.util.List;

public class BookService {
    private BookRepository bookRepository;
    private final LibraryConfiguration config;

    // 1. CONSTRUCTOR INJECTION: Used for mandatory configuration fields
    public BookService(LibraryConfiguration config) {
        this.config = config;
        System.out.println(">> [DI VERIFICATION] Constructor Injection executed for LibraryConfiguration!");
    }

    // 2. SETTER INJECTION: Used for optional or mutable dependencies
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println(">> [DI VERIFICATION] Setter Injection executed for BookRepository!");
    }

    public List<String> getAvailableBooks() {
        return bookRepository.findAllBooks();
    }

    public void registerNewBook(String title) {
        bookRepository.addBook(title);
    }

    public String getLibraryName() {
        return config.getLibraryName();
    }

    public int getMaxCapacity() {
        return config.getMaxCapacity();
    }
}