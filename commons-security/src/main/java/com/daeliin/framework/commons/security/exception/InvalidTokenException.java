package com.daeliin.framework.commons.security.exception;

public class InvalidTokenException extends Exception {
    private static final long serialVersionUID = 4544034001377379204L;
    
    public InvalidTokenException(String message) {
        super(message);
    }
}
