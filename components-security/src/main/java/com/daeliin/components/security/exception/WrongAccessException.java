package com.daeliin.components.security.exception;

public class WrongAccessException extends RuntimeException {
    private static final long serialVersionUID = -8679392797190599483L;
    
    public WrongAccessException(String message) {
        super(message);
    }
}
