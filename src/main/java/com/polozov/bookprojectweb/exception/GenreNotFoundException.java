package com.polozov.bookprojectweb.exception;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException(long id) {
        super(String.format("Genre with id %d not found", id));
    }

    public GenreNotFoundException(String genreName) {
        super(String.format("Genre with id %s not found", genreName));
    }
}
