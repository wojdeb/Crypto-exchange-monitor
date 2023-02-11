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
public class DataDTO {
    CoinbasePriceDTO data;
}
