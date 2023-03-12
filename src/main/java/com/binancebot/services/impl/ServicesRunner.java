package com.binancebot.services.impl;

import com.binancebot.model.Interval;
import com.binancebot.services.CandleQueueService;
import com.binancebot.services.CurrencyService;
import com.binancebot.services.KlineInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicesRunner {

    private static final String DEFAULT_CURRENCY = "BTCUSDT";
    private static final int PERIOD = 7;

    private final CurrencyService currencyService;
    private final KlineInfoService klineInfoService;
    private final CandleQueueService candleQueueService;

    public void run() {
        currencyService.printCurrencies();
        klineInfoService.print(DEFAULT_CURRENCY, Interval.MIN_1);
        candleQueueService.buildQueue(DEFAULT_CURRENCY, Interval.MIN_1, PERIOD);
    }
}
