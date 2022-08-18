package com.example.findahome.repository;

import com.example.findahome.models.po.Hotel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
public class HotelRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    //Intellij的bug 會顯示錯誤，但能正常運行

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void getHotelByName() {
        this.entityManager.persist(new Hotel("TestHotelRepo", "address", 5.0, "test hotel repository"));
        Hotel hotel = hotelRepository.findByName("TestHotelRepo").get(0);

        Assert.assertNotNull(hotel);
        Assert.assertEquals("TestHotelRepo", hotel.getName());
        Assert.assertEquals("address", hotel.getAddress());
        Assert.assertEquals("test hotel repository", hotel.getSummary());

    }

    @Test
    public void getHotelByAddress() {
        this.entityManager.persist(new Hotel("TestHotelRepo", "address", 5.0, "test hotel repository"));
        Hotel hotel = hotelRepository.findByAddress("address").get(0);

        Assert.assertNotNull(hotel);
        Assert.assertNotNull(hotel);
        Assert.assertEquals("TestHotelRepo", hotel.getName());
        Assert.assertEquals("address", hotel.getAddress());
        Assert.assertEquals("test hotel repository", hotel.getSummary());

    }

    @Test
    public void getGreaterHotelByRating() {
        this.entityManager.persist(new Hotel("TestHotelRepo", "address", 5.0, "test hotel repository"));
        Hotel hotel = hotelRepository.findByGreaterRating(4.0).get(0);

        Assert.assertNotNull(hotel);
        Assert.assertNotNull(hotel);
        Assert.assertEquals("TestHotelRepo", hotel.getName());
        Assert.assertEquals("address", hotel.getAddress());
        Assert.assertEquals("test hotel repository", hotel.getSummary());

    }

    @Test
    //error
    public void getHotelByNameFilter() {
        this.entityManager.persist(new Hotel("TestHotelRepo", "address", 5.0, "test hotel repository"));
        Hotel hotel = hotelRepository.findByName("TestHotelRepo", "address", 4.).get(0);

        Assert.assertNotNull(hotel);
        Assert.assertNotNull(hotel);
        Assert.assertEquals("TestHotelRepo", hotel.getName());
        Assert.assertEquals("address", hotel.getAddress());
        Assert.assertEquals("test hotel repository", hotel.getSummary());
    }
}
