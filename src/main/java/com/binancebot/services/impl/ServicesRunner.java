package com.binancebot.services.impl;

import com.binancebot.model.Interval;
import com.binancebot.services.CandleQueueService;
import com.binancebot.services.CurrencyService;
import com.binancebot.services.KlineInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.binancebot.config.Constants.DEFAULT_CURRENCY;
import static com.binancebot.config.Constants.DEFAULT_PERIOD;

@Service
@RequiredArgsConstructor
public class ServicesRunner {

    private final CurrencyService currencyService;
    private final KlineInfoService klineInfoService;
    private final CandleQueueService candleQueueService;

    public void run() {
        currencyService.printCurrencies();
        //TODO: replace default values with ones set by User
        klineInfoService.print(DEFAULT_CURRENCY, Interval.MIN_1);
        candleQueueService.buildQueue(DEFAULT_CURRENCY, Interval.MIN_1, DEFAULT_PERIOD);
    }
}
