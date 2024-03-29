package com.cemonitor.services.coinbase;

import com.cemonitor.trades.coinbase.dto.DataDTO;
import com.cemonitor.trades.coinbase.enums.CoinbaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Wojciech Dębski
 * @date 09/04/2022
 **/
@Slf4j
@Service
public class CoinbasePriceServiceImpl implements CoinbasePriceService {

    @Override
    public List<DataDTO> getCoinbaseEuroPrice() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<DataDTO> response = restTemplate.getForEntity(CoinbaseEnum.GET_EURO_PRICE.getValue(), DataDTO.class);

            return Arrays.asList(response.getBody());
        }
        catch (Exception e) {
            log.error(String.format("Cannot retrieve prices from Coinbase - %s", e.getStackTrace()));
        }

        return Collections.emptyList();
    }

    @Override
    public List<DataDTO> getCoinbaseUSDPrice() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<DataDTO> response = restTemplate.getForEntity(CoinbaseEnum.GET_USD_PRICE.getValue(), DataDTO.class);

            return Arrays.asList(response.getBody());
        }
        catch (Exception e) {
            log.error(String.format("Cannot retrieve prices from Coinbase - %s", e.getStackTrace()));
        }

        return Collections.emptyList();
    }
}
