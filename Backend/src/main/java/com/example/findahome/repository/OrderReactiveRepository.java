package com.example.findahome.repository;

import com.example.findahome.models.po.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderReactiveRepository extends ReactiveCrudRepository<Order, Integer> {

    @Query(nativeQuery = true, value = "select * from orders o where o.seq_num = ?1")
    Flux<Order> findBySeqNum(String seqNum);
}