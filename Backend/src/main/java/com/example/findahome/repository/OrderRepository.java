package com.example.findahome.repository;

import com.example.findahome.models.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(Long userId);

    List<Order> findByRoomId(Integer roomId);

    @Query(nativeQuery = true, value = "select * from orders o where o.room_id in " +
            "(select id from room_types r where r.hotel_id = ?1)")
    List<Order> findByHotelId(Integer hotelId);

}
