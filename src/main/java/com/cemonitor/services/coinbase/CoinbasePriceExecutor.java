package com.cemonitor.services.coinbase;

import com.cemonitor.model.LivePrices;
import com.cemonitor.repositories.LivePricesRepository;
import com.cemonitor.trades.coinbase.dto.DataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wojciech Dębski
 * @date 09/04/2022
 **/

@Slf4j
@Component
public class CoinbasePriceExecutor {

    @Autowired
    private CoinbasePriceService coinbasePriceService;

    private final LivePricesRepository livePricesRepository;

    public CoinbasePriceExecutor(LivePricesRepository livePricesRepository) {
        this.livePricesRepository = livePricesRepository;
    }

    @Scheduled(fixedDelay = 1000)
    private void executor() {
        List<DataDTO> coinbaseUSDPriceDTOS = coinbasePriceService.getCoinbaseUSDPrice();
        List<DataDTO> coinbaseEURPriceDTOS = coinbasePriceService.getCoinbaseEuroPrice();

        List<LivePrices> livePricesEntities = new ArrayList<>();
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();

        for (DataDTO cb : coinbaseUSDPriceDTOS) {
            livePricesEntities.add(new LivePrices(timestamp, "BTCUSDT", "Coinbase", Double.valueOf(cb.getData().getAmount())));
        }

        for (DataDTO cb : coinbaseEURPriceDTOS) {
            livePricesEntities.add(new LivePrices(timestamp, "BTCEUR", "Coinbase", Double.valueOf(cb.getData().getAmount())));
        }

        livePricesRepository.saveAll(livePricesEntities);
    }
}
