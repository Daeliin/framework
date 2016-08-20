package com.daeliin.components.security.exception;

public class AccountAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = -8679392797190599483L;
    
    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
