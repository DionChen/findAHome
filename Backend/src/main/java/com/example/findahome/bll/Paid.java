package com.example.findahome.bll;

import com.example.findahome.models.enums.OrderStatus;

public class Paid extends AstractOrderState{
    public Paid() {
        super(OrderStatus.PAID);
    }
}
