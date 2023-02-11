package com.cemonitor.trades.binance.service;

import com.cemonitor.trades.binance.dto.BinancePriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

    @Scheduled(fixedDelay = 5000)
    private void executor()
    {
        List<BinancePriceDTO> binanceEurPriceDTOS = binancePriceService.getBinanceBTCEurPrice();
        binanceEurPriceDTOS.forEach(s -> log.debug(s.toString()));

        List<BinancePriceDTO> binanceUsdPriceDTOS = binancePriceService.getBinanceBTCUSDPrice();
        binanceUsdPriceDTOS.forEach(s -> log.debug(s.toString()));
    }
}
