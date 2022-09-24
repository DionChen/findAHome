package com.example.findahome.services.Impl;

import com.example.findahome.models.exception.ApiNotFoundException;
import com.example.findahome.models.po.Order;
import com.example.findahome.repository.HotelRepository;
import com.example.findahome.repository.OrderRepository;
import com.example.findahome.repository.RoomTypeRepository;
import com.example.findahome.repository.UserRepository;
import com.example.findahome.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public Order create(Order order) {
        if (roomTypeRepository.findById(order.getRoomId()).isPresent()) {
            //check room is exist
            if (roomTypeRepository.findAvailableByRoom(order.getRoomId(), order.getStartDate(), order.getEndDate()) != null) {
                //check available
                return orderRepository.save(order);
            } else {
                throw new ApiNotFoundException("Sorry, we have no rooms at this property on your dates.");
            }
        } else {
            throw new ApiNotFoundException("Could not find the room with id =" + order.getRoomId());
        }
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order softDelete(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            order.get().setStatus(3);
            return order.get();
        } else
            throw new ApiNotFoundException("Can't find Order " + id);
    }

    public List<Order> findByUserId(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return orderRepository.findByUserId(userId);
        } else
            throw new ApiNotFoundException("Can't find user " + userId);

    }

    public List<Order> findByRoomId(Integer roomId) {
        if (roomTypeRepository.findById(roomId).isPresent()) {
            return orderRepository.findByRoomId(roomId);
        } else
            throw new ApiNotFoundException("Can't find Room " + roomId);
    }

    public List<Order> findByHotelId(Integer hotelId) {
        if(hotelRepository.findById(hotelId).isPresent()) {
            return orderRepository.findByHotelId(hotelId);
        }else
            throw new ApiNotFoundException("Can't find Hotel " + hotelId);
    }
}
