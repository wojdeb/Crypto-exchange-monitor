package com.cemonitor.services.binance;

import com.cemonitor.trades.binance.dto.BinancePriceDTO;
import com.cemonitor.trades.binance.enums.BinanceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 * @author psobieraj
 * @date 2022-04-09 10:31
 **/
@Slf4j
@Service
public class BinancePriceServiceImpl implements BinancePriceService
{
    @Override
    public List<BinancePriceDTO> getBinanceBTCEurPrice() {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<BinancePriceDTO> response = restTemplate.getForEntity(BinanceEnum.GET_EURO_PRICE.getValue(), BinancePriceDTO.class);

            return Arrays.asList(response.getBody());
        }
        catch (Exception e)
        {
            log.error(String.format("Cannot retrieve prices from Binance - %s", e.getStackTrace()));
        }

        return Collections.emptyList();
    }

    @Override
    public List<BinancePriceDTO> getBinanceBTCUSDPrice() {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<BinancePriceDTO> response = restTemplate.getForEntity(BinanceEnum.GET_USD_PRICE.getValue(), BinancePriceDTO.class);

            return Arrays.asList(response.getBody());
        }
        catch (Exception e)
        {
            log.error(String.format("Cannot retrieve prices from Binance - %s", e.getStackTrace()));
        }

        return Collections.emptyList();
    }
}
