package com.binancebot.services.impl;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import com.binancebot.converter.JsonToCandleConverter;
import com.binancebot.model.Interval;
import com.binancebot.services.CalculationService;
import com.binancebot.services.CandleQueueService;
import com.binancebot.services.TraderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class CandleQueueServiceImpl implements CandleQueueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CandleQueueServiceImpl.class);

    private final UMWebsocketClientImpl websocketClient;
    private final UMFuturesClientImpl futuresClient;

    private final JsonToCandleConverter jsonToCandleConverter;


    @Override
    public void buildQueue(String symbol, Interval interval, int period) {

        checkNotNull(symbol, "Symbol should not be null");
        checkNotNull(interval, "Interval should not be null");

        //not sure if it is correct initialization
        TraderService traderService = new TradeServiceImpl(futuresClient);
        CalculationService calculationService = new CalculationServiceImpl(period);

        websocketClient.klineStream(symbol, interval.getValue(), ((event) -> {
            // can be extracted to separate service to easily test sma retrieval logic
            var candle = jsonToCandleConverter.toCandle(event);
            var sma = calculationService.calculateSma(candle);
            traderService.onSMAUpdated(sma);
            LOGGER.info("Candle in building queue: {}", candle);
        }));
    }
}
