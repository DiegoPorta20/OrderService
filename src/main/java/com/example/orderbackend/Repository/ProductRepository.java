package com.example.orderbackend.Repository;

import com.example.orderbackend.Model.Table.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Mono<Product> findFirstByNameAndPrice(String name, float price);
}
