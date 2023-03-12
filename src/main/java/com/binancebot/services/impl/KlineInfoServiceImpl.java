package com.binancebot.services.impl;

import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import com.binancebot.converter.JsonToCandleConverter;
import com.binancebot.model.Interval;
import com.binancebot.services.KlineInfoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class KlineInfoServiceImpl implements KlineInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KlineInfoServiceImpl.class);

    private final UMWebsocketClientImpl websocketClient;
    private final JsonToCandleConverter jsonToCandleConverter;

    @Override
    public void print(String symbol, Interval interval) {
        checkNotNull(symbol, "Symbol should not be null");
        checkNotNull(interval, "Interval should not be null");

        websocketClient.klineStream(symbol, interval.getValue(), ((event) -> {
            var candle = jsonToCandleConverter.toCandle(event);
            LOGGER.info("" + candle);
        }));
    }
}
