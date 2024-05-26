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
    //ask bid
    private final Map<CryptoCurrencyExchange, Pair<BigDecimal, BigDecimal>> rates = new ConcurrentHashMap<>();
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private BigDecimal currentArbitrageValue = BigDecimal.ZERO;

    private final BigDecimal binanceCommissionMultiplier = new BigDecimal("0.999");

    public void calculate(String askPrice, String bidPrice, CryptoCurrencyExchange cryptoCurrencyExchange) {
        rates.put(cryptoCurrencyExchange, Pair.create(new BigDecimal(askPrice), new BigDecimal(bidPrice)));

        try {
            Pair<BigDecimal, BigDecimal> bnbToEth = rates.getOrDefault(CryptoCurrencyExchange.BNB_ETH, null);
            Pair<BigDecimal, BigDecimal> ethToBtc = rates.getOrDefault(CryptoCurrencyExchange.ETH_BTC, null);
            Pair<BigDecimal, BigDecimal> btcToBnb = rates.getOrDefault(CryptoCurrencyExchange.BTC_BNB, null);
            if (bnbToEth == null || ethToBtc == null || btcToBnb == null)
                return;
            BigDecimal bnbToEthAskRate = bnbToEth.first;
            BigDecimal ethToBtcBidRate = ethToBtc.second;
            BigDecimal btcToBnbBidRate = btcToBnb.second;

            currentArbitrageValue = bnbToEthAskRate.multiply(binanceCommissionMultiplier)
                    .multiply(ethToBtcBidRate).multiply(binanceCommissionMultiplier)
                    .multiply(btcToBnbBidRate).multiply(binanceCommissionMultiplier);
            LOG.info("Arbitrage rate: " + currentArbitrageValue);
        } catch (Exception e) {
            LOG.error("arbitrage rate calculation failed", e);
            //ignore
        }

    }

    public BigDecimal getCurrentArbitrageValue() {
        return currentArbitrageValue;
    }
}
