package com.adiomiDev.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(ProductErrorException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                new ErrorResponse(status, e.getMessage()), status);
    }

    @ExceptionHandler(ProductParameterConstraintException.class)
    public ResponseEntity<ErrorResponse> handleProductConstraintException(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleExceptions(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }
}
