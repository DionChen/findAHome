package com.example.findahome.bll;

import com.example.findahome.models.enums.OrderStatus;

public class Finish extends AstractOrderState{

    public Finish() {
        super(OrderStatus.FINISH);
    }
}
