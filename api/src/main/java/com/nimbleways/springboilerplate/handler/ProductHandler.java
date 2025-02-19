package com.nimbleways.springboilerplate.handler;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.entities.ProductType;

public interface ProductHandler {
    boolean supports(ProductType type);
    void handle(Product product);
}
