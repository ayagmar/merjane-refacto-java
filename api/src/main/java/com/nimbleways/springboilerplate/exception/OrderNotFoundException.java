package com.nimbleways.springboilerplate.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long orderId) {
        super(String.format("Order with id %s not found", orderId));
    }
}
