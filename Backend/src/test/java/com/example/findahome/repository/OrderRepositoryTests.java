package com.example.findahome.repository;

import com.example.findahome.models.po.Order;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.Date;

@DataJpaTest
public class OrderRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    //Intellij的bug 會顯示錯誤，但能正常運行

    @Autowired
    private  OrderRepository orderRepository;

    @Test
    public  void getOrderBySeqNum() {
        Instant startDate = new Date().toInstant();
        Instant endDate = new Date().toInstant();
        Date createdDate = new Date();
        Order newOrder = new Order(
                "123", 123L, 123, "test order", "0912345678", startDate, endDate, 1, 100, createdDate, createdDate
        );
        this.entityManager.persist(newOrder);
        Order searchOrder = orderRepository.findBySeqNum("123").get(0);
        Assert.assertNotNull(searchOrder);
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("seqNum", "123");
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("userId", 123L);
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("roomId", 123);
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("name", "test order");
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("phone", "0912345678");
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("startDate", startDate);
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("endDate", endDate);
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("status", 1);
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("cost", 100);
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("createdTime", createdDate);
        Assertions.assertThat(searchOrder).hasFieldOrPropertyWithValue("updatedTime", createdDate);
    }
}
