package com.binancebot.services;

import com.binancebot.model.Interval;

public interface KlineInfoService {
    void print(String symbol, Interval interval);
}
