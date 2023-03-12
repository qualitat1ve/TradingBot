package com.binancebot;

import com.binance.connector.futures.client.exceptions.BinanceClientException;
import com.binance.connector.futures.client.exceptions.BinanceConnectorException;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import com.binancebot.model.Candle;
import com.binancebot.model.Currency;
import com.binancebot.model.Interval;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.EvictingQueue;

import java.util.LinkedHashMap;
import java.util.List;

public class Main {
    private static final String DEFAULT_CURRENCY = "BTCUSDT";
    /**
     * Indicates number of candles for which SMA parameter is calculated
     */
    private static final int PERIOD = 7;

    /**
     * Represents the Simple Moving Average parameter
     */
    private static double SMA = 0.0d;
    private static final EvictingQueue<Candle> closedCandles = EvictingQueue.create(PERIOD);
    public static void main(String[] args) {
//        printTickers();
//        printKlineInfo();
        checkCalculationLogic();
    }

    private static void printTickers() {
        List<Currency> currencies = getCurrencies();

        if (currencies != null) {
            for (Currency c : currencies) {
                System.out.println(c);
            }
            System.out.println(currencies.size());
        } else {
            System.out.println("was not able to retrieve the data, currencies list is NULL");
        }
    }

    /**
     * Retrieves all the available 'USDⓈ-Margined' currencies and their prices
     */
    private static List<Currency> getCurrencies() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        List<Currency> currencyList = null;
        ObjectMapper mapper = new ObjectMapper();
        UMFuturesClientImpl client = new UMFuturesClientImpl();

        try {
            String result = client.market().tickerSymbol(parameters);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            currencyList = mapper.readValue(result, new TypeReference<>(){});

        } catch (BinanceConnectorException | BinanceClientException e) {
            System.out.println("something went wrong: " + e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return currencyList;
    }

    private static void printKlineInfo() {
        UMWebsocketClientImpl client = new UMWebsocketClientImpl();
        client.klineStream("btcusdt", "1m", ((event) -> {

            try {
                System.out.println(readCandleDataFromJSON(event));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
//            client.closeAllConnections();
        }));
    }

    private static Candle readCandleDataFromJSON(String candleData) throws JsonProcessingException {
        Candle candle = new Candle();
        JsonNode node = new ObjectMapper().readTree(candleData);
        candle.setSymbol(node.get("s").textValue());
        candle.setStartTime(node.get("k").get("t").asLong());
        candle.setEndTime(node.get("k").get("T").asLong());
        candle.setOpenPrice(node.get("k").get("o").asDouble());
        candle.setClosePrice(node.get("k").get("c").asDouble());
        candle.setHighPrice(node.get("k").get("h").asDouble());
        candle.setLowPrice(node.get("k").get("l").asDouble());
        candle.setClosed(node.get("k").get("x").asBoolean());
        return candle;
    }

    private static void buildCandleQueue() {
        UMWebsocketClientImpl client = new UMWebsocketClientImpl();
        client.klineStream(DEFAULT_CURRENCY, Interval.MIN_1.getValue(), ((event) -> {
            try {
                Candle candle = readCandleDataFromJSON(event);
                if (candle.isClosed()) {
                    closedCandles.add(candle);
                    if (closedCandles.remainingCapacity() == 0) {
                        double sum = closedCandles.stream().mapToDouble(Candle::getClosePrice).sum();
                        SMA = sum/PERIOD;
                    }
                }

                System.out.println(readCandleDataFromJSON(event));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
//            client.closeAllConnections();
        }));
    }

    private static void checkCalculationLogic() {
        EvictingQueue<Candle> testCandles = EvictingQueue.create(3);
        Candle candle = new Candle();
        candle.setClosePrice(1.1);

        Candle candle1 = new Candle();
        candle1.setClosePrice(2.2);

        Candle candle2 = new Candle();
        candle2.setClosePrice(3.3);

        Candle candle3 = new Candle();
        candle3.setClosePrice(4.4);

        testCandles.add(candle);
        testCandles.add(candle1);
        testCandles.add(candle2);

        double sum = testCandles.stream().mapToDouble(Candle::getClosePrice).sum();
        System.out.println("Sum expected = 6.6, sum actual = " + sum);
        double sma = sum/3;
        System.out.println("SMA expected = 2.2, SMA actual = " + sma);
        System.out.println(testCandles);
        System.out.println("*************");

        testCandles.add(candle3);
        sum = testCandles.stream().mapToDouble(Candle::getClosePrice).sum();
        sma = sum/3;
        System.out.println("Sum expected = 9.9, sum actual = " + sum);
        System.out.println("SMA expected = 3.3, SMA actual = " + sma);
        System.out.println(testCandles);
    }

}