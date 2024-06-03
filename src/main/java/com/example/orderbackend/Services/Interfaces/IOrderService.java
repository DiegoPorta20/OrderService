package com.example.orderbackend.Services.Interfaces;

import com.example.orderbackend.Model.Colection.Order;
import com.example.orderbackend.Model.Table.Product;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOrderService {
    Flux<Order> getAll();
    Mono<ResponseEntity<Order>> getById(String id);
    Mono<ResponseEntity<?>> add(Order order);
    Mono<ResponseEntity<?>> update(String id, Order order);
    Mono<Void> delete(String id);
}
