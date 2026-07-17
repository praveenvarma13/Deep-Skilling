package com.library.repository;

public class LibraryConfiguration {
    private final String libraryName;
    private final int maxCapacity;

    public LibraryConfiguration(String libraryName, int maxCapacity) {
        this.libraryName = libraryName;
        this.maxCapacity = maxCapacity;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}