package com.binancebot.model;

public enum OrderStatus {
    /**
     * The order has been accepted by the engine.
     */
    NEW,
    /**
     * A part of the order has been filled.
     */
    PARTIALLY_FILLED,
    /**
     * The order has been completed.
     */
    FILLED,
    /**
     * The order has been canceled by the user.
     */
    CANCELED,
    /**
     * Currently unused
     * (not clear from the documentation whether it is not used by Binance at this moment or it is real order's status)
     */
    PENDING_CANCEL,
    /**
     * The order was not accepted by the engine and not processed.
     */
    REJECTED,
    /**
     * 	The order was canceled according to the order type's rules
     * 	(e.g. LIMIT FOK orders with no fill, LIMIT IOC or MARKET orders that partially fill)
     */
    EXPIRED,
    /**
     * The order was canceled by the exchange due to STP trigger.
     * (e.g. an order with EXPIRE_TAKER will match with existing orders on the book
     * with the same account or same tradeGroupId)
     */
    EXPIRED_IN_MATCH
}
