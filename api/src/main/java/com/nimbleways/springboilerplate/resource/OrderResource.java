package com.nimbleways.springboilerplate.resource;

import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.services.implementations.OrderService;
import com.nimbleways.springboilerplate.services.implementations.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderResource {
    private final ProductService productService;
    private final OrderService orderService;

    @PostMapping("{orderId}/process")
    public ResponseEntity<ProcessOrderResponse> processOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        order.getItems().forEach(productService::processProduct);

        ProcessOrderResponse processOrderResponse = new ProcessOrderResponse(order.getId());
        return ResponseEntity.of(Optional.of(processOrderResponse));
    }

}
