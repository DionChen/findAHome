package com.example.findahome.controller;

import com.example.findahome.controllers.OrderController;
import com.example.findahome.models.dto.UserOrderDto;
import com.example.findahome.models.po.Order;
import com.example.findahome.models.po.User;
import com.example.findahome.security.MockSpringSecurityFilter;
import com.example.findahome.services.Impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @MockBean
    private OrderServiceImpl orderService;

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

    @Test
    //Auth一直無法過，之後再試試
    public void createOrder() throws Exception {


        UserOrderDto orderBody = new UserOrderDto();
        orderBody.setRoomId(1);
        orderBody.setName("test");
        orderBody.setPhone("12345678");
        orderBody.setStartDate(Instant.now().toString());
        orderBody.setEndDate(Instant.now().toString());
        orderBody.setCost(1000);
        when(orderService.create(any(Order.class))).thenReturn(new Order());
        this.mockMvc.perform(post("/order")
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .content(mapper.writeValueAsString(orderBody))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    //get All orderList
    @Test
    public void getOrderList() throws Exception{
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order order = new Order(123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, createdDate, createdDate
        );
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderService.findAll()).thenReturn(orderList);

        this.mockMvc.perform(get("/order/list")
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    //get Order List By user Id
    @Test
    public void getOrderListByUserId() throws Exception{
        Long userId = 1L;
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order order = new Order(123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, createdDate, createdDate
        );
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderService.findByUserId(userId)).thenReturn(orderList);

        this.mockMvc.perform(get("/order/user/" + userId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(order.getName()))
                .andExpect(jsonPath("$[0].phone").value(order.getPhone()));

    }

    //get Order List By hotel Id
    @Test
    public void getOrderListByHotelId() throws Exception{
        Integer hotelId = 100;
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order order = new Order(123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, createdDate, createdDate
        );
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderService.findByHotelId(hotelId)).thenReturn(orderList);

        this.mockMvc.perform(get("/order/hotel/list/" + hotelId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(order.getName()))
                .andExpect(jsonPath("$[0].phone").value(order.getPhone()));
    }

    //get Order List By room Id
    @Test
    public void getOrderListByRoomId() throws Exception{
        Integer roomId = 100;
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order order = new Order(123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, createdDate, createdDate
        );
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderService.findByRoomId(roomId)).thenReturn(orderList);

        this.mockMvc.perform(get("/order/room/" + roomId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(order.getName()))
                .andExpect(jsonPath("$[0].phone").value(order.getPhone()));
    }


    //soft delete order
}
