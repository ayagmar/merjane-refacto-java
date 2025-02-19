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
public class SeasonalProductHandler implements ProductHandler {
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    @Override
    public boolean supports(ProductType type) {
        return type == ProductType.SEASONAL;
    }

    @Override
    public void handle(Product product) {
        log.info("Handling seasonal product: {}", product.getId());

        LocalDate today = LocalDate.now();

        if (isWithinSeason(product, today) && product.getAvailable() > 0) {
            product.setAvailable(product.getAvailable() - 1);
        } else if (!isWithinSeason(product, today.plusDays(product.getLeadTime()))) {
            notificationService.sendOutOfStockNotification(product.getName());
            product.setAvailable(0);
        } else {
            notificationService.sendDelayNotification(product.getLeadTime(), product.getName());
        }
        productRepository.save(product);
    }

    private boolean isWithinSeason(Product product, LocalDate date) {
        return (date.isAfter(product.getSeasonStartDate()) && date.isBefore(product.getSeasonEndDate()));
    }
}
