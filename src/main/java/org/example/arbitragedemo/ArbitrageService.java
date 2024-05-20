package org.example.arbitragedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ArbitrageService {
    private final Map<CryptoCurrency, BigDecimal> rates = new ConcurrentHashMap<>();
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    public void calculate(String price, CryptoCurrency cryptoCurrency) {
        rates.put(cryptoCurrency, new BigDecimal(price));

        try {
            BigDecimal bnbRate = rates.get(CryptoCurrency.BNB);
            BigDecimal btcRate = rates.get(CryptoCurrency.BTC);
            BigDecimal ethRate = rates.get(CryptoCurrency.ETH);

            //calculations
        } catch (Exception e) {
            //ignore
        }
//        for (CryptoCurrency currency : rates.keySet()) {
//            LOG.warn(currency.name() + " rate: " + rates.get(currency));
//        }
    }


}
