package com.binancebot.services.impl;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binancebot.model.Currency;
import com.binancebot.services.CurrencyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final ObjectMapper objectMapper;
    private final UMFuturesClientImpl umFuturesClient;

    @Override
    public List<Currency> getCurrencies() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        try {
            var result = umFuturesClient.market().tickerSymbol(parameters);
            return objectMapper.readValue(result, new TypeReference<>() {
            });

        } catch (Exception e) {
            throw new RuntimeException("Could not get currencies", e);
        }
    }

    @Override
    public void printCurrencies() {
        var currencies = this.getCurrencies();

        LOGGER.info("Currencies size: {}", currencies.size());
        currencies.forEach(c -> LOGGER.info("" + c));
    }
}
