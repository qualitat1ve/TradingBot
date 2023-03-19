package com.binancebot.services.impl;

import com.binancebot.model.Candle;
import com.binancebot.services.CalculationService;
import com.google.common.collect.EvictingQueue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.google.common.base.Preconditions.checkArgument;

public class CalculationServiceImpl implements CalculationService {

    private final int period;
    private final EvictingQueue<Candle> closedCandles;

    public CalculationServiceImpl(int period) {
        checkArgument(period > 0, "Period must be a positive value");
        this.period = period;
        this.closedCandles = EvictingQueue.create(period);
    }

    @Override
    public BigDecimal getSma(Candle candle) {
        var sma = BigDecimal.ZERO;
        if (candle.getIsClosed()) {
            closedCandles.add(candle);
            if (closedCandles.remainingCapacity() == 0) {
                BigDecimal sum = closedCandles.stream().map(Candle::getClosePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                sma = sum.divide(BigDecimal.valueOf(period), RoundingMode.CEILING);
            }
        }
        return sma;
    }
}
