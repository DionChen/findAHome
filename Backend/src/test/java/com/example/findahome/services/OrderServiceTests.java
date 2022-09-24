package com.example.findahome.services;

import com.example.findahome.models.exception.ApiNotFoundException;
import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.Order;
import com.example.findahome.models.po.RoomType;
import com.example.findahome.models.po.User;
import com.example.findahome.repository.HotelRepository;
import com.example.findahome.repository.OrderRepository;
import com.example.findahome.repository.RoomTypeRepository;
import com.example.findahome.repository.UserRepository;
import com.example.findahome.services.Impl.OrderServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderServiceTests {

    @Mock
    OrderRepository orderRepository;

    @Mock
    RoomTypeRepository roomTypeRepository;

    @Mock
    HotelRepository hotelRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void createOrder() {
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Instant InvalidStartDate = Instant.parse("2022-09-04T00:00:00.00Z");
        Instant InvalidEndDate = Instant.parse("2022-09-05T00:00:00.00Z");
        Date createdDate = new Date();
        Order successOrder = new Order(123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, createdDate, createdDate
        );
        Order invalidRoomIdOrder = new Order(123L, 1234, "error order", "0912345678", startDate, endDate, 1, 100, createdDate, createdDate
        );
        Order noAvailableOrder = new Order(123L, 123, "error order", "0912345678", InvalidStartDate, InvalidEndDate, 1, 100, createdDate, createdDate

        );
        when(roomTypeRepository.findById(123)).thenReturn(java.util.Optional.of(new RoomType()));
        when(roomTypeRepository.findAvailableByRoom(successOrder.getRoomId(), successOrder.getStartDate(), successOrder.getEndDate())).thenReturn(new RoomType());
        when(roomTypeRepository.findById(1234)).thenReturn(Optional.empty());
        when(roomTypeRepository.findAvailableByRoom(noAvailableOrder.getRoomId(), noAvailableOrder.getStartDate(), noAvailableOrder.getEndDate())).thenReturn(null);
        when(orderRepository.save(successOrder)).thenReturn(successOrder);

        Order order = orderService.create(successOrder);
        Assert.assertNotNull(order);
        Assert.assertEquals("test order", order.getName());
        Assert.assertEquals("0912345678", order.getPhone());
        Exception roomIdErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () -> orderService.create(invalidRoomIdOrder));
        Assert.assertEquals("Could not find the room with id =" + invalidRoomIdOrder.getRoomId(), roomIdErrorMessage.getMessage());
        Exception roomUnavailableErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () -> orderService.create(noAvailableOrder));
        Assert.assertEquals("Sorry, we have no rooms at this property on your dates.", roomUnavailableErrorMessage.getMessage());

    }

    @Test
    public void softDeleteOrder() {
        when(orderRepository.findById(123)).thenReturn(Optional.of(new Order()));
        Order successOrder = orderService.softDelete(123);
        Assert.assertNotNull(successOrder);
        Exception invalidOrderIdMessage = Assert.assertThrows(ApiNotFoundException.class, () -> orderService.softDelete(123123));
        Assert.assertEquals("Can't find Order 123123", invalidOrderIdMessage.getMessage());
    }

    @Test
    public void findByUserId() {
        Long userId = 1L;
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(orderRepository.findByUserId(userId)).thenReturn(orderList);

        List<Order> result = orderService.findByUserId(userId);
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void findByErrorUserId() {
        Long userId = 1L;
        Long errorUserId = 100L;
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(orderRepository.findByUserId(userId)).thenReturn(orderList);


        Exception errorUserIdErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () ->  orderService.findByUserId(errorUserId));
        Assert.assertEquals("Can't find user " + errorUserId,  errorUserIdErrorMessage.getMessage());

    }

    @Test
    public void findByRoomId() {
        Integer roomId = 100;
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        when(roomTypeRepository.findById(eq(roomId))).thenReturn(Optional.of(new RoomType()));
        when(orderRepository.findByRoomId(eq(roomId))).thenReturn(orderList);

        List<Order> result = orderService.findByRoomId(roomId);
        Assert.assertFalse(result.isEmpty());
    }


    @Test
    public void findByErrorRoomId() {
        Integer roomId = 100;
        Integer errorRoomId = 1000;

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        when(roomTypeRepository.findById(eq(roomId))).thenReturn(Optional.of(new RoomType()));
        when(orderRepository.findByRoomId(eq(roomId))).thenReturn(orderList);


        Exception errorRoomIdErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () -> orderService.findByRoomId(errorRoomId));
        Assert.assertEquals("Can't find Room " + errorRoomId,  errorRoomIdErrorMessage.getMessage());
    }

    @Test
    public void findByHotelId() {
        Integer hotelId = 100;
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(new Hotel()));
        when(orderRepository.findByHotelId(hotelId)).thenReturn(orderList);

        List<Order> result = orderService.findByHotelId(hotelId);
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void findByErrorHotelId() {
        Integer hotelId = 100;
        Integer errorHotelId = 1000;
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(new Hotel()));
        when(orderRepository.findByHotelId(hotelId)).thenReturn(orderList);

        Exception errorRoomIdErrorMessage = Assert.assertThrows(ApiNotFoundException.class, () -> orderService.findByHotelId(errorHotelId));
        Assert.assertEquals("Can't find Hotel " + errorHotelId,  errorRoomIdErrorMessage.getMessage());
    }
}