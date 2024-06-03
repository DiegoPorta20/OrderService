package com.example.orderbackend.Services.Implementation;

import com.example.orderbackend.Model.Colection.Order;
import com.example.orderbackend.Model.Table.Product;
import com.example.orderbackend.Repository.ProductRepository;
import com.example.orderbackend.Services.Interfaces.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<ResponseEntity<Product>> getById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Product>> add(Product product) {
        return productRepository.save(product)
                .map(productSave -> new ResponseEntity<>(productSave, HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(product, HttpStatus.NOT_ACCEPTABLE));
    }

    @Override
    public Mono<ResponseEntity<Product>> update(Long id, Product product) {
        return productRepository.findById(id)
                .flatMap(productSave -> {
                    product.setId(id);
                    return productRepository.save(product)
                            .map(productUpdate -> new ResponseEntity<>(productUpdate,HttpStatus.ACCEPTED));
                }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return productRepository.deleteById(id);
    }
}
