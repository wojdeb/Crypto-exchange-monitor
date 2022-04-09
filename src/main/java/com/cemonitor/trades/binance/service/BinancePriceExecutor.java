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

//    @Scheduled(initialDelay = 1000 * 30, fixedDelay=Long.MAX_VALUE)
    @Scheduled(fixedDelay = 5000)
    private void executor()
    {
        List<BinancePriceDTO> binancePriceDTOS = binancePriceService.getBinancePrices();
        binancePriceDTOS.forEach(s -> log.debug(s.toString()));
    }
}
