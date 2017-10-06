package com.daeliin.components.persistence.exception;

public class PersistentResourceNotFoundException extends RuntimeException {

    public PersistentResourceNotFoundException() {
    }

    public PersistentResourceNotFoundException(String message) {
        super(message);
    }
}
