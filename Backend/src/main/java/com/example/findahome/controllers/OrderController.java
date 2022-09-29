package com.example.findahome.controllers;

import com.example.findahome.models.dto.UpdateOrderDto;
import com.example.findahome.models.dto.UserOrderDto;
import com.example.findahome.models.enums.OrderStatus;
import com.example.findahome.models.po.Order;
import com.example.findahome.services.Impl.OrderServiceImpl;
import com.example.findahome.services.Impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@PreAuthorize("hasRole('USER') or hasRole('HOTEL_ADMIN') or hasRole('ADMIN')")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody UserOrderDto userOrderDto, @AuthenticationPrincipal UserDetailsImpl user) {
        Date now = new Date();
        System.out.println(userOrderDto);
        Order newOrder = new Order(user.getId(), userOrderDto.getRoomId(), userOrderDto.getName(), userOrderDto.getPhone(), Instant.parse(userOrderDto.getStartDate()), Instant.parse(userOrderDto.getEndDate()), OrderStatus.UNPAID.getStatusNum(), userOrderDto.getCost(), userOrderDto.getPetType(), userOrderDto.getPetNum(), now, now);
        return ResponseEntity.ok(orderService.create(newOrder));
    }

//    @PostMapping
//    public ResponseEntity<Order> createOrder(@Valid @RequestBody UserOrderDto userOrderDto) {
//        Date now = new Date();
//        System.out.println(userOrderDto);
//        Order newOrder = new Order(1L, userOrderDto.getRoomId(), userOrderDto.getName(), userOrderDto.getPhone(), Instant.parse(userOrderDto.getStartDate()), Instant.parse(userOrderDto.getEndDate()), OrderStatus.UNPAID.getStatusNum(), userOrderDto.getCost(), userOrderDto.getPetType(), userOrderDto.getPetNum(), now, now);
//        return ResponseEntity.ok(orderService.create(newOrder));
//    }

    @PutMapping("{orderId}")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody UpdateOrderDto updateOrderDto, @PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.findOneAndUpdate(updateOrderDto, orderId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> getOrderList() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrderListByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }

    @GetMapping("/hotel/list/{hotelId}")
    public ResponseEntity<List<Order>> getOrderListByHotelId(@PathVariable Integer hotelId) {
        return ResponseEntity.ok(orderService.findByHotelId(hotelId));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Order>> getOrderListByRoomId(@PathVariable Integer roomId) {
        return ResponseEntity.ok(orderService.findByRoomId(roomId));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Order> softDeleteOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.softDelete(id));
    }


}
