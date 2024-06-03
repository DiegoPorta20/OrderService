package com.example.orderbackend.Services.Interfaces;

import com.example.orderbackend.Model.Table.Customer;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Flux<Customer> getAll();
    Mono<ResponseEntity<Customer>> getById(Long id);
    Mono<ResponseEntity<Customer>> add(Customer order);
    Mono<ResponseEntity<Customer>> update(Long id, Customer order);
    Mono<Void> delete(Long id);
}
