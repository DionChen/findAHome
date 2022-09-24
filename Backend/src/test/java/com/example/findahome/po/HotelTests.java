package com.example.findahome.po;

import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.RoomType;
import com.example.findahome.repository.HotelRepository;
import com.example.findahome.repository.RoomTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelTests {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

    /**
     * create Hotel
     */
    @Test
    @Order(1)
    public void createHotel() {
        Hotel newHotel = new Hotel("TestHotel",1L,
                "test address", 4.5, "test hotel", "table;chair");
        hotelRepository.save(newHotel);
        Hotel searchHotel = hotelRepository.findByName("TestHotel").get(0);
        Assert.assertEquals("TestHotel", searchHotel.getName());
        Assert.assertEquals("test address", searchHotel.getAddress());
        Assert.assertEquals("test hotel", searchHotel.getSummary());
        Assert.assertNotEquals("fackName", searchHotel.getName());

    }

    /**
     * create room
     */
    @Test
    @Order(2)
    public void createRoom() {

        Date now = new Date();
        Hotel hotel = new Hotel("TestHotel2", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        hotelRepository.save(hotel);
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        pets.put("cate", 3);

        RoomType room = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now, now);
        roomTypeRepository.save(room);
        Hotel roomHotel = hotelRepository.findByName("TestHotel2").get(0);
        RoomType searchRoom = roomTypeRepository.findByHotel(hotel).get(0);
        Assert.assertEquals("Double bed room", searchRoom.getRoomName());
        Assert.assertEquals(roomHotel, searchRoom.getHotel());
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
