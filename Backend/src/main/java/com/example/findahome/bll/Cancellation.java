package com.example.findahome.bll;

import com.example.findahome.models.enums.OrderStatus;

public class Cancellation extends AstractOrderState{

    public Cancellation() {
        super(OrderStatus.CANCELLATION);
    }

}
