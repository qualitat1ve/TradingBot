package com.binancebot.services;

import com.binancebot.model.Interval;

public interface CandleQueueService {

    void buildQueue(String symbol, Interval interval, int period);
}
