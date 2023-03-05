package com.cemonitor.trades.coinbase.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Wojciech Dębski
 * @date 09/04/2022
 **/

@Data
@Getter
@Setter
public class CoinbasePriceDTO {
    private String base;
    private String amount;
    private String currency;

    @Override
    public String toString()
    {
        return "CoinbasePriceDTO" +
                "{" +
                "symbol='" + base + '\'' +
                "currency='" + currency + '\'' +
                ", price='" + amount + '\'' +
                '}';
    }
}
