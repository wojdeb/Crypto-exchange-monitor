package com.cemonitor.trades.coinbase.service;

import com.cemonitor.trades.coinbase.dto.DataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(fixedDelay = 5000)
    private void executor()
    {
        List<DataDTO> coinbaseUSDPriceDTOS = coinbasePriceService.getCoinbaseUSDPrice();
        coinbaseUSDPriceDTOS.forEach(s -> log.debug(s.toString()));

        List<DataDTO> coinbaseEURPriceDTOS = coinbasePriceService.getCoinbaseEuroPrice();
        coinbaseEURPriceDTOS.forEach(s -> log.debug(s.toString()));
    }
}
