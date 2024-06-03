package com.example.orderbackend.Services.Interfaces;

import com.example.orderbackend.Model.Colection.Order;
import com.example.orderbackend.Model.Table.Product;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Flux<Product> getAll();
    Mono<ResponseEntity<Product>> getById(Long id);
    Mono<ResponseEntity<Product>> add(Product product);
    Mono<ResponseEntity<Product>> update(Long id, Product product);
    Mono<Void> delete(Long id);
}
