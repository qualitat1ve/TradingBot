package com.binancebot.services.impl;

import com.binancebot.model.Candle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculationServiceImplTest {

    @Test
    public void shouldThrowExceptionIfPeriodIsNegative() {
        // When
        var e = assertThrows(IllegalArgumentException.class, () -> new CalculationServiceImpl(-1));
        // Then
        assertEquals("Period must be a positive value", e.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfPeriodIsZero() {
        // When
        var e = assertThrows(IllegalArgumentException.class, () -> new CalculationServiceImpl(0));
        // Then
        assertEquals("Period must be a positive value", e.getMessage());
    }

    @ParameterizedTest
    @MethodSource("smaTestParameters")
    public void shouldCorrectlyGetSmaValue(int period, List<Candle> candles, BigDecimal expectedSma) {
        // Given
        CalculationServiceImpl calculationService = new CalculationServiceImpl(period);
        // When
        BigDecimal result = BigDecimal.valueOf(-1);
        for (Candle candle : candles) {
            result = calculationService.getSma(candle);
        }

        // Then
        assertEquals(expectedSma, result);
    }

    private static Stream<Arguments> smaTestParameters() {
        return Stream.of(
                Arguments.of(3, List.of(closedCandle(1.1), closedCandle(2.2)), BigDecimal.ZERO),
                Arguments.of(3, List.of(openCandle(1.1), closedCandle(2.2), closedCandle(3.3)), BigDecimal.ZERO),
                Arguments.of(3, List.of(closedCandle(1.1), closedCandle(2.2), closedCandle(3.3)), BigDecimal.valueOf(2.2)),
                Arguments.of(3, List.of(closedCandle(1.1), closedCandle(2.2), closedCandle(3.3), closedCandle(4.4)), BigDecimal.valueOf(3.3)),
                Arguments.of(3, List.of(closedCandle(1.1), closedCandle(2.2), openCandle(3.3), closedCandle(4.4)), BigDecimal.valueOf(2.6))
        );
    }

    private static Candle closedCandle(double closePrice) {
        return Candle.builder()
                .isClosed(true)
                .closePrice(BigDecimal.valueOf(closePrice))
                .build();
    }

    private static Candle openCandle(double openPrice) {
        return Candle.builder()
                .isClosed(false)
                .openPrice(BigDecimal.valueOf(openPrice))
                .build();
    }

}
