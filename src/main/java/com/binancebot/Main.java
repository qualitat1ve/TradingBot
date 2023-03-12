package com.binancebot;

import com.binancebot.model.Candle;
import com.google.common.collect.EvictingQueue;

public class Main {


    // Here just to keep it safe and move to test later
    private static void checkCalculationLogic() {
        EvictingQueue<Candle> testCandles = EvictingQueue.create(3);
        Candle candle = Candle.builder()
                .closePrice(1.1)
                .build();

        Candle candle1 = Candle.builder()
                .closePrice(2.2)
                .build();

        Candle candle2 = Candle.builder()
                .closePrice(3.3)
                .build();

        Candle candle3 = Candle.builder()
                .closePrice(4.4)
                .build();

        testCandles.add(candle);
        testCandles.add(candle1);
        testCandles.add(candle2);

        double sum = testCandles.stream().mapToDouble(Candle::getClosePrice).sum();
        System.out.println("Sum expected = 6.6, sum actual = " + sum);
        double sma = sum / 3;
        System.out.println("SMA expected = 2.2, SMA actual = " + sma);
        System.out.println(testCandles);
        System.out.println("*************");

        testCandles.add(candle3);
        sum = testCandles.stream().mapToDouble(Candle::getClosePrice).sum();
        sma = sum / 3;
        System.out.println("Sum expected = 9.9, sum actual = " + sum);
        System.out.println("SMA expected = 3.3, SMA actual = " + sma);
        System.out.println(testCandles);
    }

}
