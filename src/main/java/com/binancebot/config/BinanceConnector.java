package com.binancebot.config;

import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMWebsocketClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.binancebot.config.Constants.*;

@Configuration
public class BinanceConnector {

    @Bean
    public UMFuturesClientImpl futuresClient() {
        return new UMFuturesClientImpl(TESTNET_API_KEY, TESTNET_SECRET_KEY, TESTNET_BASE_URL);
    }

    @Bean
    public UMWebsocketClientImpl websocketClient() {
        return new UMWebsocketClientImpl();
    }
}
