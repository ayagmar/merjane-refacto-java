package com.nimbleways.springboilerplate.exception;

public class ProductNotSupportedException extends RuntimeException {
    public ProductNotSupportedException(String message) {
        super(message);
    }
}
