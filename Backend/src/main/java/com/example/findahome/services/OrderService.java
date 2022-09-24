package com.example.findahome.services;

import com.example.findahome.models.po.Order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    Order findById(Integer id);

    List<Order> findAll();

}
