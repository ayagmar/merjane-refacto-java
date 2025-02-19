package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.entities.ProductType;
import com.nimbleways.springboilerplate.handler.ExpiredProductHandler;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.utils.Annotations.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@UnitTest
public class ExpiredProductHandlerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ExpiredProductHandler expiredProductHandler;

    private Product expiredProduct;

    @BeforeEach
    void setup() {
        expiredProduct = new Product(1L, 0, 5, ProductType.EXPIRABLE, "Milk", LocalDate.now().minusDays(1), null, null);
    }

    @Test
    public void testExpiredProductProcessing() {
        expiredProductHandler.handle(expiredProduct);

        assertEquals(0, expiredProduct.getAvailable());
        verify(notificationService, times(1)).sendExpirationNotification("Milk", expiredProduct.getExpiryDate());
        verify(productRepository, times(1)).save(expiredProduct);
    }
}
