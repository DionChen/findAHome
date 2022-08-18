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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Order(1)
    public void getRoomByHotel() {
        Date now = new Date();
        Hotel hotel = new Hotel("TestHotel2", "test address", 4.5, "test hotel 2");
        this.entityManager.persist(hotel);
        List<String> pets = new ArrayList();
        pets.add("dog");
        pets.add("cat");
        this.entityManager.persist(new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 2, 1, now, now));

        RoomType searchRoom = roomTypeRepository.findByHotel(hotel).get(0);
        Assert.assertNotNull(searchRoom);
        Assert.assertEquals("Double bed room", searchRoom.getRoomName());
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("roomName", "Double bed room");
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("price", 100.0);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("discount", 0.8);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("area", 100);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("num", 2);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("windows", 1);
    }

    @Test
    @Order(2)
    public void getRoomByRoomName() {
        Date now = new Date();
        Hotel hotel = new Hotel("TestHotel2", "test address", 4.5, "test hotel 2");
        this.entityManager.persist(hotel);
        List<String> pets = new ArrayList();
        pets.add("dog");
        pets.add("cat");
        this.entityManager.persist(new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 2, 1, now, now));

        RoomType searchRoom = roomTypeRepository.findByRoomName("Double bed room").get(0);
        Assert.assertNotNull(searchRoom);
        Assert.assertEquals("Double bed room", searchRoom.getRoomName());
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("roomName", "Double bed room");
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("price", 100.0);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("discount", 0.8);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("area", 100);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("num", 2);
        Assertions.assertThat(searchRoom).hasFieldOrPropertyWithValue("windows", 1);
    }

    @Test
    @Order(3)
    public void clean() {
        roomTypeRepository.deleteAll();
        hotelRepository.deleteAll();
        Assertions.assertThat(roomTypeRepository.findAll()).isEmpty();
        Assertions.assertThat(hotelRepository.findAll()).isEmpty();
    }
}
