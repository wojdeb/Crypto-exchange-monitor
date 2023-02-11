package com.cemonitor.trades.coinbase.enums;

import lombok.Getter;

/**
 * @author Wojciech Dębski
 * @date 09/04/2022
 **/

@Getter
public enum CoinbaseEnum {

    GET_EURO_PRICE("https://api.coinbase.com/v2/prices/spot?currency=EUR"),
    GET_USD_PRICE("https://api.coinbase.com/v2/prices/spot?currency=USD");

    private final String value;

    CoinbaseEnum(String value) {
        this.value = value;
    }

}
