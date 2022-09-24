package com.example.findahome.bll;

import com.example.findahome.models.enums.OrderStatus;
import com.example.findahome.security.jwt.AuthEntryPointJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AstractOrderState implements OrderStatusInterface{

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    private OrderStatus status;

    public AstractOrderState(OrderStatus status) {
        this.status = status;
    }
    @Override
    public OrderStatusInterface submit() {
        logger.error("Submit error: permission denied.");
        return this;
    }

    @Override
    public OrderStatusInterface update() {
        logger.error("Update error: permission denied.");

        return this;
    }

    @Override
    public OrderStatusInterface pay() {
        logger.error("Pay error: permission denied.");

        return this;
    }

    @Override
    public OrderStatusInterface cancel() {
        logger.error("Cancel error: permission denied.");

        return this;
    }
}
