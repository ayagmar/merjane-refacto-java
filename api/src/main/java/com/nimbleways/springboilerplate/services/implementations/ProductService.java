package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.exception.ProductNotSupportedException;
import com.nimbleways.springboilerplate.handler.ProductHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final List<ProductHandler> productHandlers;

    public void processProduct(Product product) {
        ProductHandler handler = productHandlers.stream()
                .filter(h -> h.supports(product.getType()))
                .findFirst()
                .orElseThrow(() -> new ProductNotSupportedException("No handler for type: " + product.getType()));

        handler.handle(product);
    }
}
