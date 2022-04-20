package com.adiomiDev.store.exception;

public class ProductParameterConstraintException extends RuntimeException{
    public ProductParameterConstraintException() {
        super();
    }

    public ProductParameterConstraintException(String message) {
        super(message);
    }
}
