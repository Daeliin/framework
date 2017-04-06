package com.daeliin.components.core.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1850701161767930740L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
