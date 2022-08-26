package com.example.findahome.services;

import com.example.findahome.models.po.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderReactiveService {
    void create(Order order);

    Flux<Order> findBySeqNum(String seqNum);

    Mono<Order> findById(Integer id);

    Flux<Order> findAll();

    Mono<Void> delete(Integer id);
}
