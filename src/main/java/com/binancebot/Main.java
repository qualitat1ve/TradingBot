package com.binancebot;

import com.binancebot.model.Candle;
import com.google.common.collect.EvictingQueue;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {


    // Here just to keep it safe and move to test later
    private static void checkCalculationLogic() {
        EvictingQueue<Candle> testCandles = EvictingQueue.create(3);
        Candle candle = Candle.builder()
                .closePrice(BigDecimal.valueOf(1.1))
                .build();

        Candle candle1 = Candle.builder()
                .closePrice(BigDecimal.valueOf(2.2))
                .build();

        Candle candle2 = Candle.builder()
                .closePrice(BigDecimal.valueOf(3.3))
                .build();

        Candle candle3 = Candle.builder()
                .closePrice(BigDecimal.valueOf(4.4))
                .build();

        testCandles.add(candle);
        testCandles.add(candle1);
        testCandles.add(candle2);

        BigDecimal sum = testCandles.stream().map(Candle::getClosePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Sum expected = 6.6, sum actual = " + sum);
        BigDecimal sma = sum.divide(BigDecimal.valueOf(3), RoundingMode.CEILING);
        System.out.println("SMA expected = 2.2, SMA actual = " + sma);
        System.out.println(testCandles);
        System.out.println("*************");

        testCandles.add(candle3);
        sum = testCandles.stream().map(Candle::getClosePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        sma = sum.divide(BigDecimal.valueOf(3), RoundingMode.CEILING);
        System.out.println("Sum expected = 9.9, sum actual = " + sum);
        System.out.println("SMA expected = 3.3, SMA actual = " + sma);
        System.out.println(testCandles);
    }

}
