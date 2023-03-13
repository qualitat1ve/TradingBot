package com.binancebot.services;

import com.binancebot.model.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> getCurrencies();

    void printCurrencies();
}
