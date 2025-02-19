package com.nimbleways.springboilerplate.controllers;

import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderResourceIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private List<Product> allProducts;

    @BeforeEach
    void setUp() {
        // Read from JSON file
        allProducts = TestUtils.readJsonFile("/products.json", this.getClass());
        productRepository.saveAll(allProducts);
    }

    @Test
    public void processOrderShouldUpdateProductAvailability() throws Exception {
        // Create an order with one product
        Product product = allProducts.get(0);
        product.setAvailable(5);
        product = productRepository.save(product);

        Set<Product> orderItems = new HashSet<>();
        orderItems.add(product);

        Order order = new Order();
        order.setItems(orderItems);
        order = orderRepository.save(order);

        mockMvc.perform(post("/api/v1/orders/{orderId}/process", order.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk());

        Product updatedProduct = productRepository.findById(product.getId()).get();
        assertEquals(Optional.of(4), updatedProduct.getAvailable());
    }

    @Test
    public void processNonExistentOrderShouldReturn404() throws Exception {
        mockMvc.perform(post("/api/v1/orders/{orderId}/process", 999)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}
