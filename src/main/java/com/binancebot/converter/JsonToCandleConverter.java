package com.binancebot.converter;

import com.binancebot.model.Candle;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
                    .openPrice(Optional.ofNullable(node).map(n -> n.get("k")).map(n -> n.get("o")).map(JsonNode::asDouble).orElse(null))
                    .closePrice(Optional.ofNullable(node).map(n -> n.get("k")).map(n -> n.get("c")).map(JsonNode::asDouble).orElse(null))
                    .highPrice(Optional.ofNullable(node).map(n -> n.get("k")).map(n -> n.get("h")).map(JsonNode::asDouble).orElse(null))
                    .lowPrice(Optional.ofNullable(node).map(n -> n.get("k")).map(n -> n.get("l")).map(JsonNode::asDouble).orElse(null))
                    .isClosed(Optional.ofNullable(node).map(n -> n.get("k")).map(n -> n.get("x")).map(JsonNode::asBoolean).orElse(null))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Could not convert JSON to Candle", e);
        }
    }
}
