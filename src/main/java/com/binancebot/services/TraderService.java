package com.binancebot.services;

import com.binancebot.model.Order;

import java.math.BigDecimal;

public interface TraderService {

    /**
     * Performs operation specified in the order's {@link Order#getAction() action}
     * @param order set of {@code Order}'s parameters to be executed
     * @return String (JSON) representation of server's response, contains orderId, which serves for
     * order's status tracking
     */
    String executeOrder(Order order);

    /**
     * Indicates that SMA computation complete so that we can place orders
     * @param sma Simple Moving Average value, becomes a basis for an order's price calculation
     */
    void onSMAUpdated(BigDecimal sma);
}
