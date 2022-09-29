package com.example.findahome.controller;


import com.example.findahome.controllers.RoomTypeController;
import com.example.findahome.models.dto.RoomDto;
import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.RoomType;
import com.example.findahome.models.po.User;
import com.example.findahome.security.MockSpringSecurityFilter;
import com.example.findahome.services.Impl.RoomTypeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomTypeController.class)
public class RoomTypeControllerTests {

    @MockBean
    private RoomTypeServiceImpl roomTypeService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    //create
    @Test
    public void createRoomType() throws Exception {
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

        when(roomTypeService.create(any(RoomDto.class))).thenReturn(newRoomType);

        this.mockMvc.perform(post("/room")
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .content(mapper.writeValueAsString(roomData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value(roomData.getRoomName()))
                .andExpect(jsonPath("$.hotel.userId").value(hotel.getUserId()))
                .andExpect(jsonPath("$.hotel.name").value(hotel.getName()))
                .andExpect(jsonPath("$.hotel.address").value(hotel.getAddress()))
                .andExpect(jsonPath("$.hotel.rating").value(hotel.getRating()))
                .andExpect(jsonPath("$.hotel.summary").value(hotel.getSummary()))
                .andExpect(jsonPath("$.hotel.facilities").value(hotel.getFacilities()));
    }

    //update
    @Test
    public void updateRoomType() throws Exception {
        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);

        Integer roomId = 100;
        RoomType newRoomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        RoomDto roomData = new RoomDto();
        roomData.setHotelId(100);
        roomData.setRoomName("Double bed room");
        roomData.setPrice(100.0);
        roomData.setDiscount(0.8);
        roomData.setArea(100);
        roomData.setPetType(pets);
        roomData.setWindows(1);

        when(roomTypeService.update(eq(roomId), any(RoomDto.class))).thenReturn(newRoomType);

        this.mockMvc.perform(put("/room/" + roomId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .content(mapper.writeValueAsString(roomData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value(roomData.getRoomName()))
                .andExpect(jsonPath("$.hotel.userId").value(hotel.getUserId()))
                .andExpect(jsonPath("$.hotel.name").value(hotel.getName()))
                .andExpect(jsonPath("$.hotel.address").value(hotel.getAddress()))
                .andExpect(jsonPath("$.hotel.rating").value(hotel.getRating()))
                .andExpect(jsonPath("$.hotel.summary").value(hotel.getSummary()))
                .andExpect(jsonPath("$.hotel.facilities").value(hotel.getFacilities()));
    }

    //get room by id
    @Test
    public void getRoomDetailById() throws Exception {
        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);

        Integer roomId = 100;
        RoomType newRoomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());

        when(roomTypeService.findById(eq(roomId))).thenReturn(newRoomType);

        this.mockMvc.perform(get("/room/" + roomId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value(newRoomType.getRoomName()))
                .andExpect(jsonPath("$.hotel.userId").value(hotel.getUserId()))
                .andExpect(jsonPath("$.hotel.name").value(hotel.getName()))
                .andExpect(jsonPath("$.hotel.address").value(hotel.getAddress()))
                .andExpect(jsonPath("$.hotel.rating").value(hotel.getRating()))
                .andExpect(jsonPath("$.hotel.summary").value(hotel.getSummary()))
                .andExpect(jsonPath("$.hotel.facilities").value(hotel.getFacilities()));
    }


    //get available room by hotelId
    @Test
    public void getAvailableRoomByhotelId() throws Exception {
        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        String petType = "dog";
        Integer petNum = 1;
        pets.put("dog", 2);

        Integer hotelId = 100;
        RoomType newRoomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
       List<RoomType> roomList = new ArrayList<>();
       roomList.add(newRoomType);

        Instant searchStartDate = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant searchEndDate = Instant.parse("2022-09-03T00:00:00.00Z");

        when(roomTypeService.findAvailableByHotelId(eq(hotelId), eq(petType), eq(petNum), eq(searchStartDate), eq(searchEndDate))).thenReturn(roomList);

        this.mockMvc.perform(get("/room/available/" + hotelId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("petType",petType)
                        .param("petNum",petNum.toString())
                        .param("startDate", searchStartDate.toString())
                        .param("endDate", searchEndDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomName").value(newRoomType.getRoomName()))
                .andExpect(jsonPath("$[0].hotel.userId").value(hotel.getUserId()))
                .andExpect(jsonPath("$[0].hotel.name").value(hotel.getName()))
                .andExpect(jsonPath("$[0].hotel.address").value(hotel.getAddress()))
                .andExpect(jsonPath("$[0].hotel.rating").value(hotel.getRating()))
                .andExpect(jsonPath("$[0].hotel.summary").value(hotel.getSummary()))
                .andExpect(jsonPath("$[0].hotel.facilities").value(hotel.getFacilities()));
    }

    //get room list by hotelid
    @Test
    public void getRoomListByHotelId() throws Exception {
        Hotel hotel = new Hotel("TestHotel", 1L, "test address", 4.5, "test hotel 2", "table;chair");
        Map<String, Integer> pets = new HashMap<>();
        pets.put("dog", 2);

        Integer hotelId = 100;
        RoomType newRoomType = new RoomType(hotel, "Double bed room", 100.0, 0.8, 100, pets, 1, now(), now());
        List<RoomType> roomList = new ArrayList();
        roomList.add(newRoomType);

        when(roomTypeService.findByHotel(eq(hotelId))).thenReturn(roomList);


        this.mockMvc.perform(get("/room/list/" + hotelId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomName").value(newRoomType.getRoomName()))
                .andExpect(jsonPath("$[0].hotel.userId").value(hotel.getUserId()))
                .andExpect(jsonPath("$[0].hotel.name").value(hotel.getName()))
                .andExpect(jsonPath("$[0].hotel.address").value(hotel.getAddress()))
                .andExpect(jsonPath("$[0].hotel.rating").value(hotel.getRating()))
                .andExpect(jsonPath("$[0].hotel.summary").value(hotel.getSummary()))
                .andExpect(jsonPath("$[0].hotel.facilities").value(hotel.getFacilities()));

    }
}
