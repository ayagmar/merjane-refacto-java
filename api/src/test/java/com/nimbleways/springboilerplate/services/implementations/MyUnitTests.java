package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.entities.ProductType;
import com.nimbleways.springboilerplate.handler.NormalProductHandler;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.utils.Annotations.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@UnitTest
public class MyUnitTests {

    @Mock
    private NotificationService notificationService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private NormalProductHandler normalProductHandler; // Inject Normal Product Handler

    private ProductService productService;

    @Test
    public void testNormalProductProcessing() {
        Product product = new Product(null, 15, 1, ProductType.NORMAL, "RJ45 Cable", null, null, null);

        productService = new ProductService(List.of(normalProductHandler));

        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.processProduct(product);

        assertEquals(0, product.getAvailable());
        verify(productRepository, times(1)).save(product);
        verifyNoInteractions(notificationService);
    }

    @Test
    public void testOutOfStockNormalProductProcessing() {
        Product product = new Product(null, 15, 0, ProductType.NORMAL, "USB Dongle", null, null, null);

        productService = new ProductService(List.of(normalProductHandler));

        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.processProduct(product);

        assertEquals(0, product.getAvailable());
        verify(notificationService, times(1)).sendDelayNotification(15, "USB Dongle"); // Delay notification expected
        verify(productRepository, times(1)).save(product);
    }
}