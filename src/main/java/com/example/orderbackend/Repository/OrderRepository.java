package com.example.orderbackend.Repository;

import com.example.orderbackend.Model.Colection.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveMongoRepository<Order,String> {

}
