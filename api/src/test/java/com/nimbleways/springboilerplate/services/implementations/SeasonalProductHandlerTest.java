package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.entities.ProductType;
import com.nimbleways.springboilerplate.handler.SeasonalProductHandler;
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
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(SpringExtension.class)
@UnitTest
public class SeasonalProductHandlerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SeasonalProductHandler seasonalProductHandler;

    private Product seasonalProduct;

    @BeforeEach
    void setup() {
        seasonalProduct = new Product(2L, 15, 10, ProductType.SEASONAL, "Strawberries", null,
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(10));
    }

    @Test
    public void testSeasonalProductInStockInSeason() {
        seasonalProductHandler.handle(seasonalProduct);
        assertEquals(9, seasonalProduct.getAvailable());
        verify(productRepository, times(1)).save(seasonalProduct);
        verifyNoInteractions(notificationService);
    }

    @Test
    public void testSeasonalProductOutOfSeason() {
        seasonalProduct.setSeasonEndDate(LocalDate.now().minusDays(1));  // Out of season
        seasonalProductHandler.handle(seasonalProduct);

        assertEquals(0, seasonalProduct.getAvailable());
        verify(notificationService, times(1)).sendOutOfStockNotification("Strawberries");
        verify(productRepository, times(1)).save(seasonalProduct);
    }
}