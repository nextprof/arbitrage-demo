package org.example.arbitragedemo.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.arbitragedemo.ArbitrageService;
import org.example.arbitragedemo.BinanceDto;
import org.example.arbitragedemo.CryptoCurrency;
import org.example.arbitragedemo.CryptoWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;

@Service
public class EthWebSocketHandler implements WebSocketHandler, CryptoWebSocket {

    @Autowired
    private WebSocketClient webSocketClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ArbitrageService arbitrageService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean connect() {
        try {
            webSocketClient.doHandshake(this,
                            "wss://stream.binance.com:9443/ws/etheur@trade")
                    .get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        LOG.info("Binance ETH-EUR web socket connection established");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {

        try {
            BinanceDto dto = objectMapper.readValue(message.getPayload().toString(), BinanceDto.class);
            LOG.info("Binance ETH-EUR web socket message received price: {}", dto.p());
            arbitrageService.calculate(dto.p(), CryptoCurrency.ETH);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        LOG.error("Transport error", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        LOG.info("Binance ETH-EUR web socket connection closed");
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
