package com.example.findahome.repository;

import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.Order;
import com.example.findahome.models.po.RoomType;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.util.DateUtil.now;

@DataJpaTest
public class OrderRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    //Intellij的bug 會顯示錯誤，但能正常運行

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private OrderRepository orderRepository;

    //findByUserId
    @Test
    public void getOrderByUserId() {
        Hotel hotel = new Hotel("TestHotel", 1L, "address", 5.0, "test hotel repository", "table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");

        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L, roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, "dog", 1, now(), now());
        this.entityManager.persist(order);

        List<Order> result = orderRepository.findByUserId(1L);
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals("dog", result.get(0).getPetType());
        Assert.assertEquals("test order", result.get(0).getName());
        Assert.assertEquals("0912345678", result.get(0).getPhone());
        Assert.assertEquals("dog", result.get(0).getPetType());
    }

    @Test
    public void getOrderByErrorUserId() {
        Long errorUserId = 1000L;
        Hotel hotel = new Hotel("TestHotel", 1L, "address", 5.0, "test hotel repository", "table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");

        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L, roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, "dog", 1, now(), now());
        this.entityManager.persist(order);

        List<Order> result = orderRepository.findByUserId(errorUserId);
        Assert.assertTrue(result.isEmpty());
    }

    //findByRoomId
    @Test
    public void getOrderByRoomId() {
        Hotel hotel = new Hotel("TestHotel", 1L, "address", 5.0, "test hotel repository", "table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");

        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L, roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, "dog", 1, now(), now());
        this.entityManager.persist(order);

        List<Order> result = orderRepository.findByRoomId(roomType.getId());
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals("dog", result.get(0).getPetType());
        Assert.assertEquals("test order", result.get(0).getName());
        Assert.assertEquals("0912345678", result.get(0).getPhone());
        Assert.assertEquals("dog", result.get(0).getPetType());
    }

    //findByRoomId
    @Test
    public void getOrderByErrorRoomId() {
        Integer errorRoomId = 1000;
        Hotel hotel = new Hotel("TestHotel", 1L, "address", 5.0, "test hotel repository", "table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");

        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L, roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, "dog", 1, now(), now());
        this.entityManager.persist(order);

        List<Order> result = orderRepository.findByRoomId(errorRoomId);
        Assert.assertTrue(result.isEmpty());

    }

    //findByHotelId
    @Test
    public void getOrderByHotelId() {
        Hotel hotel = new Hotel("TestHotel", 1L, "address", 5.0, "test hotel repository", "table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");

        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L, roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, "dog", 1, now(), now());
        this.entityManager.persist(order);

        List<Order> orderList = orderRepository.findByHotelId(hotel.getId());
        Assert.assertFalse(orderList.isEmpty());
        Assert.assertEquals("dog", orderList.get(0).getPetType());
        Assert.assertEquals("test order", orderList.get(0).getName());
        Assert.assertEquals("0912345678", orderList.get(0).getPhone());
        Assert.assertEquals("dog", orderList.get(0).getPetType());
    }

    @Test
    public void getOrderByErrorHotelId() {
        Integer errorHotelId = 1000;
        Hotel hotel = new Hotel("TestHotel", 1L, "address", 5.0, "test hotel repository", "table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");

        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L, roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, "dog", 1, now(), now());
        this.entityManager.persist(order);

        List<Order> orderList = orderRepository.findByHotelId(errorHotelId);
        Assert.assertTrue(orderList.isEmpty());
    }
}