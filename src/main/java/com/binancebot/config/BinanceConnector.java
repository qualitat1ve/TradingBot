package com.binancebot.config;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinanceConnector {

    @Bean
    public UMFuturesClientImpl futuresClient() {
        return new UMFuturesClientImpl();
    }

    @Bean
    public UMWebsocketClientImpl websocketClient() {
        return new UMWebsocketClientImpl();
    }
}
