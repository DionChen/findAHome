package com.example.findahome.services;


import com.example.findahome.models.dto.RoomDto;
import com.example.findahome.models.exception.ApiBadRequestException;
import com.example.findahome.models.exception.ApiNotFoundException;
import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.RoomType;
import com.example.findahome.repository.HotelRepository;
import com.example.findahome.repository.RoomTypeRepository;
import com.example.findahome.services.Impl.RoomTypeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RoomTypeServiceTests {

    @Mock
    RoomTypeRepository roomTypeRepository;

    @Mock
    HotelRepository hotelRepository;


    @InjectMocks
    private RoomTypeServiceImpl roomTypeService;


    //create
    @Test
    public void createRoomType() {

        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        RoomType newRoomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());

        RoomDto roomData = new RoomDto();
        roomData.setHotelId(100);
        roomData.setRoomName("Double bed room");
        roomData.setPrice(100.0);
        roomData.setDiscount(0.8);
        roomData.setArea(100);
        roomData.setPetType(pets);
        roomData.setWindows(1);

        when(hotelRepository.findById(any())).thenReturn(Optional.of(hotel));
        when(roomTypeRepository.save(any(RoomType.class))).thenReturn(newRoomType);

        RoomType createRoom = roomTypeService.create(roomData);
        Assert.assertNotNull(createRoom);
        Assert.assertEquals("Double bed room", createRoom.getRoomName());
    }


    @Test
    public void createRoomTypeWithHotelNotFound() {
        Integer errorHotelId = 1000;
        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        RoomType newRoomType = new RoomType( hotel,"Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());

        RoomDto roomData = new RoomDto();
        roomData.setHotelId(errorHotelId);
        roomData.setRoomName("Double bed room");
        roomData.setPrice(100.0);
        roomData.setDiscount(0.8);
        roomData.setArea(100);
        roomData.setPetType(pets);
        roomData.setWindows(1);

        when(hotelRepository.findById(100)).thenReturn(Optional.of(hotel));
        when(roomTypeRepository.save(any(RoomType.class))).thenReturn(newRoomType);

        Exception hotelNotFoundErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () -> roomTypeService.create(roomData));
        Assert.assertEquals("Can't find Hotel id : " + errorHotelId, hotelNotFoundErrorMessage.getMessage());
    }

    //findAvailableByRoom
    @Test
    public void findAvailableRoom() {
        Integer roomId = 100;
        Instant bookingStart = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant bookingEnd = Instant.parse("2022-09-04T00:00:00.00Z");
        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        String petType = "dog";
        Integer petNum = 1;
        pets.put("dog", 2);
        RoomType newRoomType = new RoomType( hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());


        when(roomTypeRepository.findById(eq(roomId))).thenReturn(Optional.of(new RoomType()));
        when(roomTypeRepository.findAvailableByRoom(roomId, petType, petNum, bookingStart, bookingEnd)).thenReturn(newRoomType);

        RoomType availableRoom = roomTypeService.findAvailableByRoom(roomId, petType, petNum, bookingStart, bookingEnd);

        Assert.assertNotNull(availableRoom);
        Assert.assertEquals("Double bed room", availableRoom.getRoomName());
    }

    @Test
    public void findAvailableRoomWithErrorRoomId() {
        Integer errorRoomId = 1000;
        Instant bookingStart = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant bookingEnd = Instant.parse("2022-09-04T00:00:00.00Z");


        Exception roomNotFoundErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () -> roomTypeService.findAvailableByRoom(errorRoomId,"dog", 1, bookingStart, bookingEnd));
        Assert.assertEquals("Can't find Room Id: " + errorRoomId, roomNotFoundErrorMessage.getMessage());
    }

    @Test
    public void findAvailableRoomWithErrorDate() {
        Integer roomId = 100;
        Instant errorBookingStart = Instant.parse("2022-09-04T00:00:00.00Z");
        Instant errorBookingEnd = Instant.parse("2022-09-01T00:00:00.00Z");


        when(roomTypeRepository.findById(eq(roomId))).thenReturn(Optional.of(new RoomType()));

        Exception dateErrorMessage = Assert.assertThrows(ApiBadRequestException.class, () -> roomTypeService.findAvailableByRoom(roomId,"dog", 1, errorBookingStart, errorBookingEnd));
        Assert.assertEquals("Booking Date Error. Please check your select date.", dateErrorMessage.getMessage());
    }

    @Test
    public void findAvailableRoomByHotelId() {
        Integer hotelId = 100;
        String petType = "dog";
        Integer petNum = 1;
        Instant bookingStart = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant bookingEnd = Instant.parse("2022-09-04T00:00:00.00Z");
        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        RoomType newRoomType = new RoomType( hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        List<RoomType> roomList = new ArrayList<>();
        roomList.add(newRoomType);

        when(hotelRepository.findById(eq(hotelId))).thenReturn(Optional.of(new Hotel()));
        when(roomTypeRepository.findAvailableByHotelId(hotelId,petType, petNum, bookingStart, bookingEnd)).thenReturn(roomList);

        List<RoomType> availableRoom = roomTypeService.findAvailableByHotelId(hotelId,petType, petNum, bookingStart, bookingEnd);



        Assert.assertNotNull(availableRoom);
        Assert.assertEquals("Double bed room", availableRoom.get(0).getRoomName());
    }

    @Test
    public void findAvailableRoomByHotelIdWithErrorHotelId() {
        Integer hotelId = 100;
        String petType = "dog";
        Integer petNum = 1;
        Integer errorHotelId = 1000;
        Instant bookingStart = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant bookingEnd = Instant.parse("2022-09-04T00:00:00.00Z");
        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        RoomType newRoomType = new RoomType( hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        List<RoomType> roomList = new ArrayList<>();
        roomList.add(newRoomType);

        when(hotelRepository.findById(eq(hotelId))).thenReturn(Optional.of(new Hotel()));
        when(roomTypeRepository.findAvailableByHotelId(hotelId,petType, petNum, bookingStart, bookingEnd)).thenReturn(roomList);


        Exception roomNotFoundErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () -> roomTypeService.findAvailableByHotelId(errorHotelId,petType, petNum, bookingStart, bookingEnd));
        Assert.assertEquals("Can't find hotel Id: " + errorHotelId, roomNotFoundErrorMessage.getMessage());

    }

    @Test
    public void findAvailableRoomByHotelIdWithErrorDate() {
        String petType = "dog";
        Integer petNum = 1;
        Integer hotelId = 100;
        Instant bookingStart = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant bookingEnd = Instant.parse("2022-09-04T00:00:00.00Z");
        Instant errorBookingStart = Instant.parse("2022-09-04T00:00:00.00Z");
        Instant errorBookingEnd = Instant.parse("2022-09-01T00:00:00.00Z");

        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        RoomType newRoomType = new RoomType( hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        List<RoomType> roomList = new ArrayList<>();
        roomList.add(newRoomType);

        when(hotelRepository.findById(eq(hotelId))).thenReturn(Optional.of(new Hotel()));
        when(roomTypeRepository.findAvailableByHotelId(hotelId,petType, petNum, bookingStart, bookingEnd)).thenReturn(roomList);

        Exception roomBadRequestErrorMessage = Assert.assertThrows(ApiBadRequestException.class, () -> roomTypeService.findAvailableByHotelId(hotelId, petType, petNum, errorBookingStart, errorBookingEnd));
        Assert.assertEquals("Wrong date selected. Please check your select date." , roomBadRequestErrorMessage.getMessage());
    }



    @Test
    public void findRoomByHotelEntity() {
        Integer hotelId = 100;
        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);
        RoomType newRoomType = new RoomType( hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        List<RoomType> roomTypeList = new ArrayList<>();
        roomTypeList.add(newRoomType);

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(new Hotel()));
        when(roomTypeRepository.findByHotel(any(Hotel.class))).thenReturn(roomTypeList);

        List<RoomType> result = roomTypeService.findByHotel(hotelId);

        Assert.assertNotNull(result);
        Assert.assertEquals("Double bed room", result.get(0).getRoomName());
    }

    @Test
    public void findRoomByHotelEntityWithErrorHotelId() {
        Integer hotelId = 100;
        Integer errorHotelId = 1000;

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(new Hotel()));

        Exception hotelIdMessage = Assert.assertThrows(ApiNotFoundException.class, () -> roomTypeService.findByHotel(errorHotelId));
        Assert.assertEquals("Can't find Hotel Id : " + errorHotelId, hotelIdMessage.getMessage());
    }

}

