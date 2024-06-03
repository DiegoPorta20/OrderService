package com.example.orderbackend.Services.Implementation;

import com.example.orderbackend.Model.Table.Customer;
import com.example.orderbackend.Repository.CustomerRepository;
import com.example.orderbackend.Services.Interfaces.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Flux<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<ResponseEntity<Customer>> getById(Long id) {
        return customerRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Customer>> add(Customer user) {
        return customerRepository.save(user)
                .map(userSave -> new ResponseEntity<>(userSave,HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(user,HttpStatus.NOT_ACCEPTABLE));
    }

    @Override
    public Mono<ResponseEntity<Customer>> update(Long id, Customer user) {
        return customerRepository.findById(id)
                .flatMap(userSave -> {
                    user.setId(id);
                    return customerRepository.save(user)
                            .map(userUpdate -> new ResponseEntity<>(userUpdate,HttpStatus.ACCEPTED));
                }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return customerRepository.deleteById(id);
    }
}
