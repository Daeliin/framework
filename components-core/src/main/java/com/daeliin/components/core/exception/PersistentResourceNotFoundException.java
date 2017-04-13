package com.daeliin.components.core.exception;

public class PersistentResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1850701161767930740L;

    public PersistentResourceNotFoundException(String message) {
        super(message);
    }
}
