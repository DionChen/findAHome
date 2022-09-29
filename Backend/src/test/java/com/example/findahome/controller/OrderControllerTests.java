package com.example.findahome.controller;

import com.example.findahome.controllers.OrderController;
import com.example.findahome.models.dto.UpdateOrderDto;
import com.example.findahome.models.dto.UserOrderDto;
import com.example.findahome.models.enums.OrderStatus;
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

import static java.time.LocalTime.now;
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
    public void createOrder() throws Exception {

        UserOrderDto orderBody = new UserOrderDto();
        orderBody.setRoomId(1);
        orderBody.setName("test");
        orderBody.setPhone("12345678");
        orderBody.setStartDate(Instant.now().toString());
        orderBody.setEndDate(Instant.now().toString());
        orderBody.setCost(1000);
        orderBody.setPetType("dog");
        orderBody.setPetNum(1);
        when(orderService.create(any(Order.class))).thenReturn(new Order());
        this.mockMvc.perform(post("/order")
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .content(mapper.writeValueAsString(orderBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrder() throws Exception {
        Date now = new Date();
        String name = "test";
        String phone = "0987654321";
        Integer orderId = 100;
        UpdateOrderDto data = new UpdateOrderDto();
        data.setPhone(phone);
        data.setName(name);
        when(orderService.findOneAndUpdate(any(UpdateOrderDto.class), eq(orderId))).thenReturn(new Order(123L, 100, name, phone, Instant.now(), Instant.now(), OrderStatus.UNPAID.getStatusNum(), 100, "dog", 1, now, now));
        this.mockMvc.perform(put("/order/" + orderId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .content(mapper.writeValueAsString(data))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(123L))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.status").value(OrderStatus.UNPAID.getStatusNum()))
                .andExpect(jsonPath("$.cost").value(100))
                .andExpect(jsonPath("$.petType").value("dog"))
                .andExpect(jsonPath("$.petNum").value(1));
    }

    //get All orderList
    @Test
    public void getOrderList() throws Exception {
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order order = new Order(123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, "dog", 1, createdDate, createdDate
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
    public void getOrderListByUserId() throws Exception {
        Long userId = 1L;
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order order = new Order(123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, "dog", 1, createdDate, createdDate
        );
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderService.findByUserId(userId)).thenReturn(orderList);

        this.mockMvc.perform(get("/order/user/" + userId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(order.getName()))
                .andExpect(jsonPath("$[0].phone").value(order.getPhone()))
                .andExpect(jsonPath("$[0].petType").value("dog"));

    }

    //get Order List By hotel Id
    @Test
    public void getOrderListByHotelId() throws Exception {
        Integer hotelId = 100;
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order order = new Order(123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, "dog", 1, createdDate, createdDate
        );
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderService.findByHotelId(hotelId)).thenReturn(orderList);

        this.mockMvc.perform(get("/order/hotel/list/" + hotelId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(order.getName()))
                .andExpect(jsonPath("$[0].phone").value(order.getPhone()))
                .andExpect(jsonPath("$[0].petType").value("dog"));

    }

    //get Order List By room Id
    @Test
    public void getOrderListByRoomId() throws Exception {
        Integer roomId = 100;
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order order = new Order(123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, "dog", 1, createdDate, createdDate
        );
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderService.findByRoomId(roomId)).thenReturn(orderList);

        this.mockMvc.perform(get("/order/room/" + roomId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(order.getName()))
                .andExpect(jsonPath("$[0].phone").value(order.getPhone()))
                .andExpect(jsonPath("$[0].petType").value("dog"));

    }


    //soft delete order
    @Test
    public void softDeleteOrder() throws Exception {
        Integer orderId = 999;
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order order = new Order(123L, 123, "test order", "0912345678", startDate, endDate, OrderStatus.DELETE.getStatusNum(), 100, "dog", 1, createdDate, createdDate
        );
        when(orderService.softDelete(orderId)).thenReturn(order);

        this.mockMvc.perform(put("/order/delete/" + orderId)
                        .principal(new UsernamePasswordAuthenticationToken(new User(), null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(order.getName()))
                .andExpect(jsonPath("$.phone").value(order.getPhone()))
                .andExpect(jsonPath("$.status").value(OrderStatus.DELETE.getStatusNum()))
                .andExpect(jsonPath("$.petType").value("dog"));
    }
}
