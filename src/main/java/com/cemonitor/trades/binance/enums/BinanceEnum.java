package com.cemonitor.trades.binance.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 *
 * @author psobieraj
 * @date 2022-04-09 11:00
 **/
@Getter
public enum BinanceEnum
{
    GET_PRICES("https://api.binance.com/api/v3/ticker/price"),
    GET_EURO_PRICE("https://api.binance.com/api/v3/ticker/price?symbol=BTCEUR"),
    GET_USD_PRICE("https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT");

    private final String value;

    BinanceEnum(String value)
    {
        this.value = value;
    }
}
