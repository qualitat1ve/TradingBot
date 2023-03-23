package com.binancebot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class Order {

    private Action action;
    private String currencySymbol;
    private BigDecimal quantity;
    private BigDecimal price;
    private OrderStatus status;
    private long orderId;
    //should probably be converted into a Date
    private long timeStamp;

}
