package com.binancebot.services.impl;

import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binancebot.model.Action;
import com.binancebot.model.Order;
import com.binancebot.services.TraderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.binancebot.config.Constants.DEFAULT_CURRENCY;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TraderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);
    private final UMFuturesClientImpl futuresClient;
    private BigDecimal currentSMA = BigDecimal.ZERO;
    /**
     * supposed to be set by User via UI (by default - 1%),
     * indicates the difference in percentage between the current SMA value and the order's price
     */
    private int threshold = 1;
    /**
     * Indicates whether the orders are placed
     */
    private boolean inPosition = false;
    @Override
    public String executeOrder(Order order) {

        checkNotNull(order, "Order should not be NULL");

        String result = "NO RESULT YET";
        try {
            switch (order.getAction()) {
                case BUY:
                case SELL:
                    result = futuresClient.account().newOrder(prepareNewOrderParameters(order));
                    break;
                case QUERY:
                    result = futuresClient.account().queryOrder(prepareExistingOrderParameters(order));
                    break;
                case CANCEL:
                    result = futuresClient.account().cancelOrder(prepareExistingOrderParameters(order));
                    break;
            }
            LOGGER.info(result);
        } catch (BinanceConnectorException | BinanceClientException e) {
            LOGGER.error("Something went wrong while executing the order" + e.getMessage());
        }
        return result;
    }
    private LinkedHashMap<String, Object> prepareNewOrderParameters(Order order) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", order.getCurrencySymbol());
        parameters.put("side", order.getAction());
        parameters.put("type", "LIMIT");
        parameters.put("timeInForce", "GTC");
        parameters.put("quantity", order.getQuantity());
        parameters.put("price", order.getPrice());
        return parameters;
    }

    private LinkedHashMap<String, Object> prepareExistingOrderParameters(Order order) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", order.getCurrencySymbol());
        parameters.put("orderId", order.getOrderId());
        return parameters;
    }

    @Override
    public void onSMAUpdated(BigDecimal sma) {
        if (currentSMA.equals(sma)) {
            //skip
        } else {
            currentSMA = sma;
            //create order
            if (inPosition) {
                //skip
            } else {
                Order buyOrder = Order.builder()
                        .action(Action.BUY)
                        .price(getBuyPrice())
                        .currencySymbol(DEFAULT_CURRENCY) //should be replaced with the picked by User
                        .build();
                String buyOrderResult = executeOrder(buyOrder);
                Order sellOrder = Order.builder()
                        .action(Action.SELL)
                        .price(getSellPrice())
                        .currencySymbol(DEFAULT_CURRENCY) //should be replaced with the picked by User
                        .build();
                String sellOrderResult = executeOrder(sellOrder);
                inPosition = true;
            }
        }
    }

    private BigDecimal getBuyPrice() {
        return currentSMA.subtract(getThresholdAbsoluteValue());
    }

    private BigDecimal getSellPrice() {
        return currentSMA.add(getThresholdAbsoluteValue());
    }

    private BigDecimal getThresholdAbsoluteValue () {
        return currentSMA.multiply(new BigDecimal(threshold)).divide(new BigDecimal(100), RoundingMode.CEILING);
    }
}
