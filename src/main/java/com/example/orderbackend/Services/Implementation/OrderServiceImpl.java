package com.example.orderbackend.Services.Implementation;

import com.example.orderbackend.Model.Colection.Order;
import com.example.orderbackend.Model.Table.Customer;
import com.example.orderbackend.Model.Table.Product;
import com.example.orderbackend.Repository.CustomerRepository;
import com.example.orderbackend.Repository.OrderRepository;
import com.example.orderbackend.Repository.ProductRepository;
import com.example.orderbackend.Services.Interfaces.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Flux<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Mono<ResponseEntity<Order>> getById(String id) {
        return orderRepository.findById(id)
                .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<?>> add(Order order) {
        return ValidateProductAndCustomer(order)
                .flatMap(orderValidate -> {
                    if(orderValidate.getStatusCodeValue() == 406)
                        return Mono.just(new ResponseEntity<>("Some of the products or the customer is not correct", HttpStatus.NOT_ACCEPTABLE));
                    return orderRepository.insert(order)
                            .map(orderSave -> new ResponseEntity<>(orderSave,HttpStatus.ACCEPTED))
                            .defaultIfEmpty(new ResponseEntity<>(order,HttpStatus.NOT_ACCEPTABLE));
                });
    }

    @Override
    public Mono<ResponseEntity<?>> update(String id, Order order) {
        return ValidateProductAndCustomer(order)
                .flatMap(orderValidate -> {
                    if(orderValidate.getStatusCodeValue() == 406){
                        return Mono.just(new ResponseEntity<>("Some of the products or the customer is not correct",HttpStatus.NOT_ACCEPTABLE));
                    }
                    return orderRepository.findById(id)
                            .flatMap(orderSave -> {
                                order.setId(id);
                                return orderRepository.save(order)
                                        .map(orderUpdate -> new ResponseEntity<>(orderUpdate,HttpStatus.ACCEPTED));
                            }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return orderRepository.deleteById(id);
    }
    Mono<ResponseEntity<Order>> ValidateProductAndCustomer(Order order){
        if (order.getProducts().isEmpty() || order.getCustomer() == null) {
            return Mono.just(new ResponseEntity<>(order, HttpStatus.NOT_ACCEPTABLE));
        }

        Flux<Boolean> productValidationFlux = Flux.fromIterable(order.getProducts())
                .flatMap(product -> productRepository.findFirstByNameAndPrice(product.getName(), product.getPrice())
                        .hasElement());

        Mono<Customer> customerMono = customerRepository.findFirstByEmail(
                order.getCustomer().getEmail()
        );

        return productValidationFlux.all(valid -> valid)
                .zipWith(customerMono.hasElement())
                .flatMap(tuple -> {
                    boolean allProductsValid = tuple.getT1();
                    boolean customerValid = tuple.getT2();

                    if (allProductsValid && customerValid) {
                        System.out.println("All products and customer are valid");
                        return Mono.just(new ResponseEntity<>(order, HttpStatus.ACCEPTED));
                    } else {
                        System.out.println("Some products or customer are invalid");
                        return Mono.just(new ResponseEntity<>(order, HttpStatus.NOT_ACCEPTABLE));
                    }
                });
    }
}
