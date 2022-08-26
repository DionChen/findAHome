package com.example.findahome.services.Impl;

import com.example.findahome.models.po.Order;
import com.example.findahome.repository.OrderReactiveRepository;
import com.example.findahome.services.OrderReactiveService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
public class OrderResctiveServiceImpl implements OrderReactiveService {

    private final OrderReactiveRepository orderReactiveRepository;

    public OrderResctiveServiceImpl(OrderReactiveRepository orderReactiveRepository) {
        this.orderReactiveRepository = orderReactiveRepository;
    }

    @Override
    @Transactional
    public void create(Order order) {
        try {
            //check order
            if (false) {
                orderReactiveRepository.save(order).subscribe();
            } else {
                //check available date
                if (true) {
                    orderReactiveRepository.save(order).subscribe();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Flux<Order> findBySeqNum(String seqNum) {
        return orderReactiveRepository.findBySeqNum(seqNum);
    }

    @Override
    public Mono<Order> findById(Integer id) {
        return orderReactiveRepository.findById(id);
    }

    @Override
    public Flux<Order> findAll() {
        return orderReactiveRepository.findAll();
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return orderReactiveRepository.deleteById(id);
    }
}
