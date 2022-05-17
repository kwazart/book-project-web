package com.polozov.bookprojectweb.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException() {}
}
