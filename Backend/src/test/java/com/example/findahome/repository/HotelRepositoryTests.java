package com.example.findahome.repository;

import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.Order;
import com.example.findahome.models.po.RoomType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.util.DateUtil.now;

@DataJpaTest
public class HotelRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    //Intellij的bug 會顯示錯誤，但能正常運行

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Test
    public void getHotelByName() {

        this.entityManager.persist(new Hotel("TestHotelRepo", 1L, "address", 5.0, "test hotel repository","table;chair"));
        Hotel hotel = hotelRepository.findByName("TestHotelRepo").get(0);
        System.out.println(hotel);
        Assert.assertNotNull(hotel);
        Assert.assertEquals("TestHotelRepo", hotel.getName());
        Assert.assertEquals("address", hotel.getAddress());
        Assert.assertEquals("test hotel repository", hotel.getSummary());

    }

    @Test
    public void getHotelByAddress() {
        this.entityManager.persist(new Hotel("TestHotelRepo", 1L , "address", 5.0, "test hotel repository","table;chair"));
        Hotel hotel = hotelRepository.findByAddress("address").get(0);

        Assert.assertNotNull(hotel);
        Assert.assertNotNull(hotel);
        Assert.assertEquals("TestHotelRepo", hotel.getName());
        Assert.assertEquals("address", hotel.getAddress());
        Assert.assertEquals("test hotel repository", hotel.getSummary());

    }

    @Test
    public void getGreaterHotelByRating() {

        this.entityManager.persist(new Hotel("TestHotelRepo", 1L, "address", 5.0, "test hotel repository","table;chair"));
        Hotel hotel = hotelRepository.findByGreaterRating(4.0).get(0);

        Assert.assertNotNull(hotel);
        Assert.assertNotNull(hotel);
        Assert.assertEquals("TestHotelRepo", hotel.getName());
        Assert.assertEquals("address", hotel.getAddress());
        Assert.assertEquals("test hotel repository", hotel.getSummary());

    }

    @Test
    public void getHotelByNameFilter() {
        this.entityManager.persist(new Hotel("TestHotelRepo", 1L, "address", 5.0, "test hotel repository","table;chair"));
        Hotel hotel = hotelRepository.findByName("TestHotelRepo", "address", 4.).get(0);
        Assert.assertNotNull(hotel);
        Assert.assertNotNull(hotel);
        Assert.assertEquals("TestHotelRepo", hotel.getName());
        Assert.assertEquals("address", hotel.getAddress());
        Assert.assertEquals("test hotel repository", hotel.getSummary());
    }

    @Test
    public void getAvailableHotelFilter() {
        String keyword = "dre";
        Instant searchStartDate = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant searchEndDate = Instant.parse("2022-09-03T00:00:00.00Z");
        String petType = "dog";
        Integer petNum = 1;
        Hotel hotel = new Hotel("TestHotelRepo", 1L, "address", 5.0, "test hotel repository","table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType( hotel,"Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());


        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");
        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L,roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, now(), now());
        this.entityManager.persist(order);
        List<Hotel> hotelList = hotelRepository.findAvailableHotel(keyword, petType, petNum, searchStartDate, searchEndDate);
        Assert.assertNotNull(hotelList);
        Assert.assertEquals("TestHotelRepo",hotelList.get(0).getName());
        Assert.assertEquals("address",hotelList.get(0).getAddress());
        Assert.assertEquals("test hotel repository",hotelList.get(0).getSummary());


    }

    @Test
    public void getUnavailableHotelFilterWithKeyword() {

        String keyword = "nobody";
        Instant searchStartDate = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant searchEndDate = Instant.parse("2022-09-03T00:00:00.00Z");
        String petType = "dog";
        Integer petNum = 1;
        Hotel hotel = new Hotel("TestHotelRepo", 1L, "address", 5.0, "test hotel repository","table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType( hotel,"Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());


        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");
        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L,roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, now(), now());
        this.entityManager.persist(order);
        List<Hotel> hotelList = hotelRepository.findAvailableHotel(keyword, petType, petNum, searchStartDate, searchEndDate);
        Assert.assertTrue(hotelList.isEmpty());


    }

    @Test
    public void getUnavailableHotelFilterWithDate() {

        String keyword = "add";
        Instant searchStartDate = Instant.parse("2022-09-08T00:00:00.00Z");
        Instant searchEndDate = Instant.parse("2022-09-09T00:00:00.00Z");
        String petType = "dog";
        Integer petNum = 1;
        Hotel hotel = new Hotel("TestHotelRepo", 1L, "address", 5.0, "test hotel repository", "table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType( hotel,"Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());


        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");
        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L,roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, now(), now());
        this.entityManager.persist(order);
        List<Hotel> hotelList = hotelRepository.findAvailableHotel(keyword, petType, petNum, searchStartDate, searchEndDate);
        Assert.assertTrue(hotelList.isEmpty());

    }

    @Test
    public void getUnavailableHotelFilterWithPetType() {
        String keyword = "dre";
        Instant searchStartDate = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant searchEndDate = Instant.parse("2022-09-03T00:00:00.00Z");
        String petType = "otter";
        Integer petNum = 1;
        Hotel hotel = new Hotel("TestHotelRepo", 1L, "address", 5.0, "test hotel repository","table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType( hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());


        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");
        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L,roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, now(), now());
        this.entityManager.persist(order);
        List<Hotel> hotelList = hotelRepository.findAvailableHotel(keyword, petType, petNum, searchStartDate, searchEndDate);
        Assert.assertTrue(hotelList.isEmpty());

    }

    @Test
    public void getUnavailableHotelFilterWithPetNum() {
        String keyword = "dre";
        Instant searchStartDate = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant searchEndDate = Instant.parse("2022-09-03T00:00:00.00Z");
        String petType = "dog";
        Integer petNum = 100;
        Hotel hotel = new Hotel("TestHotelRepo", 1L, "address", 5.0, "test hotel repository","table;chair");

        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 10);
        RoomType roomType = new RoomType( hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());


        Instant orderStartDate = Instant.parse("2022-09-07T00:00:00.00Z");
        Instant orderEndDate = Instant.parse("2022-09-10T00:00:00.00Z");
        this.entityManager.persist(hotel);
        this.entityManager.persist(roomType);
        Order order = new Order(1L,roomTypeRepository.findByRoomName("Double bed room").get(0).getId(), "test order", "0912345678", orderStartDate, orderEndDate, 1, 100, now(), now());
        this.entityManager.persist(order);
        List<Hotel> hotelList = hotelRepository.findAvailableHotel(keyword, petType, petNum, searchStartDate, searchEndDate);
        Assert.assertTrue(hotelList.isEmpty());

    }


}
