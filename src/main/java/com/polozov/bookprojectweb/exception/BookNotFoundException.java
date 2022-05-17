package com.polozov.bookprojectweb.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(long id) {
        super(String.format("Book with id %s not found", id));
    }
}
