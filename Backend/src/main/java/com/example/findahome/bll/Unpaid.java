package com.example.findahome.bll;

import com.example.findahome.models.enums.OrderStatus;

public class Unpaid  extends AstractOrderState{

    public Unpaid() {
        super(OrderStatus.UNPAID);
    }
}
