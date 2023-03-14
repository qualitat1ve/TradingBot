package com.binancebot.converter;

import com.binancebot.model.Candle;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JsonToCandleConverter {

    private final ObjectMapper objectMapper;

    public Candle toCandle(String jsonValue) {
        try {
            JsonNode node = objectMapper.readTree(jsonValue);
            return Candle.builder()
                    .symbol(Optional.ofNullable(node).map(n -> n.get("s")).map(JsonNode::textValue).orElse(null))
                    .startTime(Optional.ofNullable(node).map(n -> n.get("k")).map(n -> n.get("t")).map(JsonNode::asLong).orElse(null))
                    .endTime(Optional.ofNullable(node).map(n -> n.get("k")).map(n -> n.get("T")).map(JsonNode::asLong).orElse(null))
                    .openPrice(new BigDecimal(Objects.requireNonNull(Optional.ofNullable(node).map(n -> n.get("k")).map(n -> n.get("o")).map(JsonNode::textValue).orElse(null))))
                    .closePrice(new BigDecimal(Objects.requireNonNull(Optional.of(node).map(n -> n.get("k")).map(n -> n.get("c")).map(JsonNode::textValue).orElse(null))))
                    .highPrice(new BigDecimal(Objects.requireNonNull(Optional.of(node).map(n -> n.get("k")).map(n -> n.get("h")).map(JsonNode::textValue).orElse(null))))
                    .lowPrice(new BigDecimal(Objects.requireNonNull(Optional.of(node).map(n -> n.get("k")).map(n -> n.get("l")).map(JsonNode::textValue).orElse(null))))
                    .isClosed(Optional.ofNullable(node).map(n -> n.get("k")).map(n -> n.get("x")).map(JsonNode::asBoolean).orElse(null))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Could not convert JSON to Candle", e);
        }
    }
}
