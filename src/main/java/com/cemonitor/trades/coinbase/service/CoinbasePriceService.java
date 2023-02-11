package com.cemonitor.trades.coinbase.service;

import com.cemonitor.trades.coinbase.dto.DataDTO;

import java.util.List;

/**
 * @author Wojciech Dębski
 * @date 09/04/2022
 **/

public interface CoinbasePriceService {
    public List<DataDTO> getCoinbaseEuroPrice();
    public List<DataDTO> getCoinbaseUSDPrice();
}
