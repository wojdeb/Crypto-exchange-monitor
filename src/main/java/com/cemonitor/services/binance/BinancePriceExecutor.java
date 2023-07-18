package com.cemonitor.services.binance;

import com.cemonitor.model.LivePrices;
import com.cemonitor.repositories.LivePricesRepository;
import com.cemonitor.trades.binance.dto.BinancePriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author psobieraj
 * @date 2022-04-09 11:14
 **/
@Slf4j
@Component
public class BinancePriceExecutor
{
    @Autowired
    private BinancePriceService binancePriceService;

    private final LivePricesRepository livePricesRepository;

    public BinancePriceExecutor(LivePricesRepository livePricesRepository) {
        this.livePricesRepository = livePricesRepository;
    }

    @Scheduled(fixedDelay = 1000)
    private void executor() {
        List<BinancePriceDTO> binanceEurPriceDTOS = binancePriceService.getBinanceBTCEurPrice();
        List<BinancePriceDTO> binanceUsdPriceDTOS = binancePriceService.getBinanceBTCUSDPrice();

        List<LivePrices> livePricesEntities = new ArrayList<>();
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();

        for (BinancePriceDTO b : binanceUsdPriceDTOS) {
            livePricesEntities.add(new LivePrices(timestamp, b.getSymbol(), "Binance", Double.valueOf(b.getPrice())));
        }

        for (BinancePriceDTO b : binanceEurPriceDTOS) {
            livePricesEntities.add(new LivePrices(timestamp, b.getSymbol(), "Binance", Double.valueOf(b.getPrice())));
        }

        livePricesRepository.saveAll(livePricesEntities);
    }
}
