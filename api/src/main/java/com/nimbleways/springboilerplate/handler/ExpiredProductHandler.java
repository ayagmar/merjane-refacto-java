package com.nimbleways.springboilerplate.handler;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.entities.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpiredProductHandler implements ProductHandler {
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    @Override
    public boolean supports(ProductType type) {
        return type == ProductType.EXPIRABLE;
    }

    @Override
    public void handle(Product product) {
        log.info("Handling expired product: {}", product.getId());
        if (product.getAvailable() > 0 && product.getExpiryDate().isAfter(LocalDate.now())) {
            product.setAvailable(product.getAvailable() - 1);
        } else {
            notificationService.sendExpirationNotification(product.getName(), product.getExpiryDate());
            product.setAvailable(0);
        }
        productRepository.save(product);
    }
}
