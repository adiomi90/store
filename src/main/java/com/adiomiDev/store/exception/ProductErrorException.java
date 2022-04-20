package com.adiomiDev.store.exception;

import org.springframework.http.HttpStatus;

public class ProductErrorException extends RuntimeException {

    public ProductErrorException() {
        super();
    }

    public ProductErrorException(String message) {
        super(message);
    }

    public ProductErrorException(HttpStatus status, String message) {
        this(message);
    }

}
