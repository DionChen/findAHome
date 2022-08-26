package com.example.findahome.repository;

import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.RoomType;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.*;

@DataJpaTest
public class RoomTypeRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    //Intellij的bug 會顯示錯誤，但能正常運行

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Test
    public void getRoomByHotel() {
        Date now = new Date();
        Hotel hotel = new Hotel("TestHotel2", "test address", 4.5, "test hotel 2");
        this.entityManager.persist(hotel);
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        pets.put("cate", 3);

        this.entityManager.persist(new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now, now));

        RoomType searchRoom = roomTypeRepository.findByHotel(hotel).get(0);
        Assert.assertNotNull(searchRoom);
        Assert.assertEquals("Double bed room", searchRoom.getRoomName());
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("roomName", "Double bed room");
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("price", 100.0);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("discount", 0.8);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("area", 100);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("windows", 1);
    }

    @Test
    public void getRoomByRoomName() {
        Date now = new Date();
        Hotel hotel = new Hotel("TestHotel2", "test address", 4.5, "test hotel 2");
        this.entityManager.persist(hotel);
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        pets.put("cate", 3);
        this.entityManager.persist(new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now, now));

        RoomType searchRoom = roomTypeRepository.findByRoomName("Double bed room").get(0);
        Assert.assertNotNull(searchRoom);
        Assert.assertEquals("Double bed room", searchRoom.getRoomName());
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("roomName", "Double bed room");
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("price", 100.0);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("discount", 0.8);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("area", 100);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("windows", 1);
    }

    @Test
    public void checkAvailableRoom() {

        Date now = new Date();
        Hotel hotel = new Hotel("TestHotel2", "test address", 4.5, "test hotel 2");
        this.entityManager.persist(hotel);
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        pets.put("cate", 3);
        this.entityManager.persist(new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now, now));
        RoomType searchRoom = roomTypeRepository.findByRoomName("Double bed room").get(0);
        Instant orderStartDate = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-03T00:00:00.00Z");
        Instant vacancyStartDate = Instant.parse("2022-09-04T00:00:00.00Z");
        Instant vacancyEndDate = Instant.parse("2022-09-05T00:00:00.00Z");
        this.entityManager.persist(new com.example.findahome.models.po.Order("123", 123L, searchRoom.getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, now, now));

        RoomType busyRoom = roomTypeRepository.findAvailableByRoom(searchRoom.getId(), orderStartDate, orderEndDate);
        Assert.assertNull(busyRoom);

        RoomType vacancyRoom = roomTypeRepository.findAvailableByRoom(searchRoom.getId(), vacancyStartDate, vacancyEndDate);
        Assert.assertNotNull(vacancyRoom);


    }
}
