package com.cemonitor.trades.binance.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 * @author psobieraj
 * @date 2022-04-09 10:32
 **/
@Data
@Getter
@Setter
public class BinancePriceDTO
{
    private String symbol;
    private String price;

    @Override
    public String toString()
    {
        return "BinancePriceDTO" +
                "{" +
                "symbol='" + symbol + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
