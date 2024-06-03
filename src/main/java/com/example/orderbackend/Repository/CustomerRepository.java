package com.example.orderbackend.Repository;

import com.example.orderbackend.Model.Table.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer,Long> {
    Mono<Customer> findFirstByEmail(String email);
}
