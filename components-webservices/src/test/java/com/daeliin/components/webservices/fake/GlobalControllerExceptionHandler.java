package com.daeliin.components.webservices.fake;

import com.daeliin.components.webservices.exception.PageRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public void handleNoSuchElementException() {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PageRequestException.class)
    public void handlePageRequestException() {
    }
}
