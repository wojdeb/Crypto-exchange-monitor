package com.cemonitor.trades.binance.service;

import com.cemonitor.trades.binance.dto.BinancePriceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author psobieraj
 * @date 2022-04-09 10:54
 **/
@Service
public interface BinancePriceService
{
    public List<BinancePriceDTO> getBinanceBTCEurPrice();
    public List<BinancePriceDTO> getBinanceBTCUSDPrice();
}
