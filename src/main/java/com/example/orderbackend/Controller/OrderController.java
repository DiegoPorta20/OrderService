package com.example.orderbackend.Controller;
import com.example.orderbackend.Model.Colection.Order;
import com.example.orderbackend.Services.Implementation.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("order")
public class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    @Operation(tags = {"Order"})
    public Mono<ResponseEntity<Order>> getOrderById(@PathVariable("orderId") String orderId){
        return orderService.getById(orderId);
    }
    @GetMapping("")
    @Operation(tags = {"Order"})
    public Flux<Order> getAll(){
        return orderService.getAll();
    }

    @PostMapping("")
    @Operation(tags = {"Order"})
    public Mono<ResponseEntity<Order>> createOrder(@RequestBody Order order){
        return orderService.add(order);
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Order>> updateOrder(@RequestBody Order order,@PathVariable String id){
        return orderService.update(id, order);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> deleteOrder(@PathVariable String id){
        return orderService.delete(id);
    }
}
