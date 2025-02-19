package com.nimbleways.springboilerplate.handler;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.entities.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NormalProductHandler implements ProductHandler {
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    @Override
    public boolean supports(ProductType type) {
        return type == ProductType.NORMAL;
    }

    @Override
    public void handle(Product product) {
        log.info("Handling normal product: {}", product.getId());
        if (product.getAvailable() > 0) {
            updateStock(product);
        } else {
            handleOutOfStock(product);
        }
    }

    private void updateStock(Product product) {
        product.setAvailable(product.getAvailable() - 1);
        productRepository.save(product);
    }

    private void handleOutOfStock(Product product) {
        if (product.getLeadTime() > 0) {
            log.info("Lead time reached, sending delay notification for product: {}", product.getName());
            notificationService.sendDelayNotification(product.getLeadTime(), product.getName());
            productRepository.save(product);
        }
    }
}
