package com.binancebot.model;

import lombok.Getter;

@Getter
public enum Interval {
    SEC_1("1s"),
    MIN_1("1m"),
    MIN_3("3m"),
    MIN_5("5m"),
    MIN_15("15m"),
    MIN_30("30m"),
    HOUR_1("1h"),
    HOUR_2("2h"),
    HOUR_4("4h"),
    HOUR_6("6h"),
    HOUR_8("8h"),
    HOUR_12("12h"),
    DAY_1("1d"),
    DAY_3("3d"),
    WEEK_1("1w"),
    MON_1("1M");

    private final String value;

    Interval(String value) {
        this.value = value;
    }

}
