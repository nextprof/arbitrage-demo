package org.example.arbitragedemo;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.List;

@Configuration
public class WebSocketClientConfig implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    @Lazy
    private List<CryptoWebSocket> cryptoWebSockets;
    @Autowired
    private ArbitrageService arbitrageService;
    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        for (CryptoWebSocket cryptoWebSocket : cryptoWebSockets) {
            cryptoWebSocket.connect();
        }
        Gauge.builder("arbitrage-value", () -> arbitrageService.getCurrentArbitrageValue())
                .register(meterRegistry);
    }

}
