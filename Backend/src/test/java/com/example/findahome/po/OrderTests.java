package com.example.findahome.po;

import com.example.findahome.models.po.Order;
import com.example.findahome.repository.OrderRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTests {

    @Autowired
    OrderRepository orderRepository;

    /**
     * create test
     */
    @Test
    public void createOrder() {
        Instant startDate = Instant.parse("2022-09-04T00:00:00.00Z");
        Instant endDate = Instant.parse("2022-09-06T00:00:00.00Z");
        Date createdDate = new Date();
        Order newOrder = new Order(123L, 35, "test order", "0912345678", startDate, endDate, 1, 100, "dog", 1, createdDate, createdDate
        );
        orderRepository.save(newOrder);
        Order goalOrder = orderRepository.findByUserId(123L).get(0);
        Assert.assertNotNull(goalOrder);
        Assert.assertEquals("test order", goalOrder.getName());
        Assert.assertEquals("0912345678", goalOrder.getPhone());
        Assert.assertEquals(startDate, goalOrder.getStartDate());
        Assert.assertEquals(endDate, goalOrder.getEndDate());
        Assert.assertEquals(createdDate, goalOrder.getCreatedTime());
        Assert.assertEquals(createdDate, goalOrder.getUpdatedTime());
    }

//    @After
//    public void clean() {
//        orderRepository.deleteAll();
//    }
}
