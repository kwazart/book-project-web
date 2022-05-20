package com.polozov.bookprojectweb.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(long id) {
        super(String.format("Author with id %d not found", id));
    }

    public AuthorNotFoundException(String authorName) {
        super(String.format("Author with name %s not found", authorName));
    }
}
