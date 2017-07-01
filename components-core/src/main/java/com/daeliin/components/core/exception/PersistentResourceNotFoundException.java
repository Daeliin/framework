package com.daeliin.components.core.exception;

public class PersistentResourceNotFoundException extends RuntimeException {

    public PersistentResourceNotFoundException() {
    }

    public PersistentResourceNotFoundException(String message) {
        super(message);
    }
}
