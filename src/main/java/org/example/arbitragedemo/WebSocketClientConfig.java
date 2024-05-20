package org.example.arbitragedemo;

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

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        for (CryptoWebSocket cryptoWebSocket : cryptoWebSockets) {
            cryptoWebSocket.connect();
        }
    }

}
