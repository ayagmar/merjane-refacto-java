package com.nimbleways.springboilerplate.repositories;


import com.nimbleways.springboilerplate.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
