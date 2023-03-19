package com.binancebot.services;

import com.binancebot.model.Candle;

import java.math.BigDecimal;

public interface CalculationService {
    BigDecimal getSma(Candle candle);
}
