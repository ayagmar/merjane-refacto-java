package com.nimbleways.springboilerplate.repositories;


import com.nimbleways.springboilerplate.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findById(Long orderId);
}
