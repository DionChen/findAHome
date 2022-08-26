package com.example.findahome.controllers;

import com.example.findahome.models.po.Order;
import com.example.findahome.services.Impl.OrderResctiveServiceImpl;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/admin/order")
public class OrderReactiveController {

    private final OrderResctiveServiceImpl orderReactiveService;

    public OrderReactiveController(OrderResctiveServiceImpl orderReactiveService) {
        this.orderReactiveService = orderReactiveService;
    }

    @PostMapping
    public void createOrder() {
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order newOrder = new Order(
                "123", 123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, createdDate, createdDate
        );
        orderReactiveService.create(newOrder);
    }

    @GetMapping(value = "/{id}")
    public Mono<Order> getOrderById(@PathVariable("id") Integer id) {
        return orderReactiveService.findById(id);
    }

    @GetMapping("/all")
    public Flux<Order> getAllOrder() {
        return orderReactiveService.findAll();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteOrderById(@PathVariable("id") Integer id) {
        return orderReactiveService.delete(id);
    }
}
