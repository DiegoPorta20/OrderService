package com.example.orderbackend;

import com.example.orderbackend.Model.Colection.Order;
import com.example.orderbackend.Model.Table.Customer;
import com.example.orderbackend.Model.Table.Product;
import com.example.orderbackend.Repository.CustomerRepository;
import com.example.orderbackend.Repository.OrderRepository;
import com.example.orderbackend.Repository.ProductRepository;
import com.example.orderbackend.Services.Implementation.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrderSuccess() {
        Product product = new Product(1L,"Product1", 100.0F, 2);
        Customer customer = new Customer(1L,"John", "Doe", "john.doe@example.com");
        Order order = new Order();
        order.setProducts(Arrays.asList(product));
        order.setCustomer(customer);

        when(productRepository.findFirstByNameAndPrice("Product1", 100.0F)).thenReturn(Mono.just(product));
        when(customerRepository.findFirstByEmail("john.doe@example.com")).thenReturn(Mono.just(customer));
        when(orderRepository.insert(any(Order.class))).thenReturn(Mono.just(order));

        Mono<ResponseEntity<Order>> result = orderService.add(order);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.ACCEPTED)
                .verifyComplete();
    }

    @Test
    public void testAddOrderInvalidProduct() {
        Product product = new Product(1L,"Product1", 100.0F, 2);
        Customer customer = new Customer(1L,"John", "Doe", "john.doe@example.com");
        Order order = new Order();
        order.setProducts(Arrays.asList(product));
        order.setCustomer(customer);

        when(productRepository.findFirstByNameAndPrice("Product1", 100.0F)).thenReturn(Mono.empty());
        when(customerRepository.findFirstByEmail("john.doe@example.com")).thenReturn(Mono.just(customer));

        Mono<ResponseEntity<Order>> result = orderService.add(order);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE)
                .verifyComplete();
    }

    @Test
    public void testGetByIdSuccess() {
        Order order = new Order();
        order.setId("123");

        when(orderRepository.findById("123")).thenReturn(Mono.just(order));

        Mono<ResponseEntity<Order>> result = orderService.getById("123");

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK && response.getBody().getId().equals("123"))
                .verifyComplete();
    }

    @Test
    public void testGetByIdNotFound() {
        when(orderRepository.findById("123")).thenReturn(Mono.empty());

        Mono<ResponseEntity<Order>> result = orderService.getById("123");

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();
    }
}