package com.example.android.booklistingapp;

/**
 * Created by Lariveg on 5/06/2017.
 */

public class Book {
    // Title of the book
    private String Title;

    // Author of the book
    private String Author;

    public Book(String title, String author) {
        Title = title;
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }
}
