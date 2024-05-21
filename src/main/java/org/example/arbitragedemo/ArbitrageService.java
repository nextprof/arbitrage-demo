package org.example.arbitragedemo;

import io.micrometer.core.instrument.Gauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ArbitrageService {
    private final Map<CryptoCurrencyExchange, BigDecimal> rates = new ConcurrentHashMap<>();
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private BigDecimal currentArbitrageValue = BigDecimal.ZERO;

    public void calculate(String price, CryptoCurrencyExchange cryptoCurrencyExchange) {
        rates.put(cryptoCurrencyExchange, new BigDecimal(price));

        try {
            BigDecimal bnbToEthRate = rates.get(CryptoCurrencyExchange.BNB_ETH);
            BigDecimal ethToBtcRate = rates.get(CryptoCurrencyExchange.ETH_BTC);
            BigDecimal btcToBnbRate = rates.get(CryptoCurrencyExchange.BTC_BNB);

            currentArbitrageValue = bnbToEthRate.multiply(ethToBtcRate).multiply(btcToBnbRate);
//            LOG.error("Arbitrage rate: " + arbitrage);
        } catch (Exception e) {
            //ignore
        }

    }

    public BigDecimal getCurrentArbitrageValue() {
        return currentArbitrageValue;
    }
}
