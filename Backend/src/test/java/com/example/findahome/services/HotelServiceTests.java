package com.example.findahome.services;

import com.example.findahome.models.exception.ApiNotFoundException;
import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.User;
import com.example.findahome.repository.HotelRepository;
import com.example.findahome.repository.UserRepository;
import com.example.findahome.services.Impl.HotelServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class HotelServiceTests {

    @Mock
    HotelRepository hotelRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;


    @Test
    public void createHotel() {
        User user = new User("test", "test@gmail.com", "123");
        Hotel newHotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel", "table;chair");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.save(newHotel)).thenReturn(newHotel);

        Hotel serviceHotel = hotelService.create(newHotel);

        Assert.assertNotNull(serviceHotel);
        Assert.assertEquals(newHotel.getName(), serviceHotel.getName());
        Assert.assertEquals(newHotel.getUserId(), serviceHotel.getUserId());
        Assert.assertEquals(newHotel.getAddress(), serviceHotel.getAddress());
        Assert.assertEquals(newHotel.getSummary(), serviceHotel.getSummary());
        Assert.assertEquals(newHotel.getFacilities(), serviceHotel.getFacilities());

    }

    @Test
    public void createHotelWhenUserNotFound() {
        User user = new User("test", "test@gmail.com", "123");
        Hotel newHotel = new Hotel("TestHotel", 100L, "test address", 4.5, "test hotel", "table;chair");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.save(newHotel)).thenReturn(newHotel);

        Exception userIdErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () -> hotelService.create(newHotel));
        Assert.assertEquals("Can't find User " + newHotel.getUserId() , userIdErrorMessage.getMessage());

    }

    @Test
    public void updateHotelData() {
        Integer oldHotelId = 100;
        Hotel oldHotel = new Hotel("TestHotel", 100L, "test address", 4.5, "test hotel", "table;chair");
        Hotel updateHotelData = new Hotel("Update Hotel", 100L, "test address", 4.5, "test hotel", "table;chair");


        when(hotelRepository.findById(oldHotelId)).thenReturn(Optional.of(oldHotel));
        when(hotelRepository.save(updateHotelData)).thenReturn(updateHotelData);

        Hotel updateHotel = hotelService.update(oldHotelId, updateHotelData);

        Assert.assertEquals(updateHotelData.getName(), updateHotel.getName());
        Assert.assertEquals(updateHotelData.getUserId(), updateHotel.getUserId());
        Assert.assertEquals(updateHotelData.getAddress(), updateHotel.getAddress());
        Assert.assertEquals(updateHotelData.getSummary(), updateHotel.getSummary());
        Assert.assertEquals(updateHotelData.getFacilities(), updateHotel.getFacilities());
    }

    @Test
    public void updateHotelDataWhenHotelNotFound() {
        Integer oldHotelId = 400;
        Hotel oldHotel = new Hotel("TestHotel", 100L, "test address", 4.5, "test hotel", "table;chair");
        Hotel updateHotelData = new Hotel("Update Hotel", 100L, "test address", 4.5, "test hotel", "table;chair");


        when(hotelRepository.findById(100)).thenReturn(Optional.of(oldHotel));
        when(hotelRepository.save(updateHotelData)).thenReturn(updateHotelData);

        Exception userIdErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () -> hotelService.update(oldHotelId, updateHotelData));
        Assert.assertEquals(("Can't find Hotel " + oldHotelId) , userIdErrorMessage.getMessage());

    }
    //findHotel (目前在repository中都已經測試完了，假設有改service 的設定才需要測試)

    //findOneById
    @Test
    public void findHotelById() {
        Integer hotelId = 100;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(new Hotel()));

        Hotel hotel = hotelService.findOneById(hotelId);

        Assert.assertNotNull(hotel);
    }

    @Test
    public void findHotelByIdWhenErrorHotelId() {
        Integer hotelId = 100;
        when(hotelRepository.findById(10)).thenReturn(Optional.of(new Hotel()));

        Hotel hotel = hotelService.findOneById(hotelId);

        Assert.assertNull(hotel);
    }
}
