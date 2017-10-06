package com.daeliin.components.persistence.exception;

public final class PersistentResourceAlreadyExistsException extends RuntimeException {

    public PersistentResourceAlreadyExistsException(String message) {
        super(message);
    }
}
