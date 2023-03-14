package com.binancebot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

@Getter
@Jacksonized
@SuperBuilder
@RequiredArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Candle {
    private final String symbol;
    private final Long startTime;
    private final Long endTime;
    private final BigDecimal openPrice;
    private final BigDecimal closePrice;
    private final BigDecimal highPrice;
    private final BigDecimal lowPrice;
    private final Boolean isClosed;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("symbol", symbol)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .append("openPrice", openPrice)
                .append("closePrice", closePrice)
                .append("highPrice", highPrice)
                .append("lowPrice", lowPrice)
                .append("isClosed", isClosed)
                .toString();
    }
}
