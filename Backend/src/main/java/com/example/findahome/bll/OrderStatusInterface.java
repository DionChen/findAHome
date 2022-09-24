package com.example.findahome.bll;

public interface OrderStatusInterface {

    OrderStatusInterface submit();

    OrderStatusInterface update();

    OrderStatusInterface pay();

    OrderStatusInterface cancel();

}