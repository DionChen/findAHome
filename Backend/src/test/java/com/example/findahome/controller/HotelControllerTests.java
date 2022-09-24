package com.example.findahome.controller;

import com.example.findahome.controllers.HotelController;
import com.example.findahome.models.dto.HotelDto;
import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.User;
import com.example.findahome.security.MockSpringSecurityFilter;
import com.example.findahome.services.Impl.HotelServiceImpl;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HotelController.class)
public class HotelControllerTests {

    @MockBean
    private HotelServiceImpl hotelService;

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

    //create test
    @Test
    public void createHotel() throws Exception {

        HotelDto hotelData = new HotelDto();
        hotelData.setName("test hotel");
        hotelData.setAddress("Address");
        hotelData.setRating(4.0);
        hotelData.setSummary("This is a test summary of hotel");
        hotelData.setFacilities("table;chair;book;bed");

        Hotel hotel = new Hotel(hotelData.getName(), 123L, hotelData.getAddress(), hotelData.getRating(), hotelData.getSummary(), hotelData.getFacilities());


        when(hotelService.create(any(Hotel.class))).thenReturn(hotel);

        this.mockMvc.perform(post("/hotel")
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .content(mapper.writeValueAsString(hotelData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(123L))
                .andExpect(jsonPath("$.name").value(hotelData.getName()))
                .andExpect(jsonPath("$.address").value(hotelData.getAddress()))
                .andExpect(jsonPath("$.rating").value(hotelData.getRating()))
                .andExpect(jsonPath("$.summary").value(hotelData.getSummary()))
                .andExpect(jsonPath("$.facilities").value(hotelData.getFacilities()));
//            .andDo(print());

    }

    //update test
    @Test
    public void updateHotel() throws Exception {

        Integer oldHotelId = 100;
        HotelDto hotelUpdateData = new HotelDto();
        hotelUpdateData.setName("test hotel");
        hotelUpdateData.setAddress("Address");
        hotelUpdateData.setRating(4.0);
        hotelUpdateData.setSummary("This is a test summary of hotel");
        hotelUpdateData.setFacilities("table;chair;book;bed");

        Hotel newHotel = new Hotel(hotelUpdateData.getName(), 123L, hotelUpdateData.getAddress(), hotelUpdateData.getRating(), hotelUpdateData.getSummary(), hotelUpdateData.getFacilities());


        when(hotelService.update(eq(oldHotelId), any(Hotel.class))).thenReturn(newHotel);


        this.mockMvc.perform(put("/hotel/" + oldHotelId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .content(mapper.writeValueAsString(hotelUpdateData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(123L))
                .andExpect(jsonPath("$.name").value(newHotel.getName()))
                .andExpect(jsonPath("$.address").value(newHotel.getAddress()))
                .andExpect(jsonPath("$.rating").value(newHotel.getRating()))
                .andExpect(jsonPath("$.summary").value(newHotel.getSummary()))
                .andExpect(jsonPath("$.facilities").value(newHotel.getFacilities()));

    }

    @Test
    public void getHotelById() throws Exception {
        Integer hotelId = 100;
        Hotel hotel = new Hotel("test hotel", 123L, "Address", 4.0, "This is a test summary of hotel", "table;chair;book;bed");

        when(hotelService.findOneById(eq(hotelId))).thenReturn(hotel);

        this.mockMvc.perform(get("/hotel/" + hotelId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(123L))
                .andExpect(jsonPath("$.name").value(hotel.getName()))
                .andExpect(jsonPath("$.address").value(hotel.getAddress()))
                .andExpect(jsonPath("$.rating").value(hotel.getRating()))
                .andExpect(jsonPath("$.summary").value(hotel.getSummary()))
                .andExpect(jsonPath("$.facilities").value(hotel.getFacilities()));

    }

    @Test
    public void getHotelListBySearchFilter() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        Hotel hotel = new Hotel("test hotel", 123L, "Address", 4.0, "This is a test summary of hotel", "table;chair;book;bed");
        hotelList.add(hotel);
        String searchKeyword = "test";
        String searchType = "dog";
        Integer searchNum = 1;
        Instant searchStartDate = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant searchEndDate = Instant.parse("2022-09-03T00:00:00.00Z");


        when(hotelService.findHotel(eq(searchKeyword), eq(searchType), eq(searchNum), eq(searchStartDate), eq(searchEndDate))).thenReturn(hotelList);

        this.mockMvc.perform(get("/hotel/list")
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("keyword", searchKeyword)
                        .param("type", searchType)
                        .param("num", searchNum.toString())
                        .param("start", searchStartDate.toString())
                        .param("end", searchEndDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(123L))
                .andExpect(jsonPath("$[0].name").value(hotel.getName()))
                .andExpect(jsonPath("$[0].address").value(hotel.getAddress()))
                .andExpect(jsonPath("$[0].rating").value(hotel.getRating()))
                .andExpect(jsonPath("$[0].summary").value(hotel.getSummary()))
                .andExpect(jsonPath("$[0].facilities").value(hotel.getFacilities()));
    }

    @Test
    public void unavailableGetHotelListWithoutKeyword() throws Exception {
        List<Hotel> hotelList = new ArrayList<>();
        Hotel hotel = new Hotel("test hotel", 123L, "Address", 4.0, "This is a test summary of hotel", "table;chair;book;bed");
        hotelList.add(hotel);
        String searchKeyword = "test";
        String searchType = "dog";
        Integer searchNum = 1;
        Instant searchStartDate = Instant.parse("2022-09-01T00:00:00.00Z");
        Instant searchEndDate = Instant.parse("2022-09-03T00:00:00.00Z");


        when(hotelService.findHotel(eq(searchKeyword), eq(searchType), eq(searchNum), eq(searchStartDate), eq(searchEndDate))).thenReturn(hotelList);

        this.mockMvc.perform(get("/hotel/list")
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("type", searchType)
                        .param("num", searchNum.toString())
                        .param("start", searchStartDate.toString())
                        .param("end", searchEndDate.toString()))
                .andExpect(status().is(400));
    }
}
