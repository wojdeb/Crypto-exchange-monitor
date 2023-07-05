package com.cemonitor.services.db;

import com.cemonitor.model.HourStatisticsEntity;
import com.cemonitor.model.LivePrices;
import com.cemonitor.repositories.HourStatisticsRepository;
import com.cemonitor.repositories.LivePricesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wojciech Dębski
 * @date 15/03/2023
 **/

@Slf4j
@AllArgsConstructor
@Service
public class HourStatisticsCalculationService {
    private LivePricesRepository livePricesRepository;
    private HourStatisticsRepository hourStatisticsRepository;

    private double calculateAverage(String exchange, String currencyPair) {
        List<LivePrices> prices = livePricesRepository.findAllByExchangeAndCurrencyPair(exchange, currencyPair);

        return prices.stream().mapToDouble(LivePrices::getPrice).sum() / prices.size();
    }

    private double calculateHighestSpread(String baseExchange, String competitiveExchange, String currencyPair) {
        List<LivePrices> basePrices = livePricesRepository.findAllByExchangeAndCurrencyPair(baseExchange, currencyPair);
        List<LivePrices> competitivePrices = livePricesRepository.findAllByExchangeAndCurrencyPair(competitiveExchange, currencyPair);
        double highestSpread = 0;

        log.info("Calculating highest spread per hour...");

        for(int i = 0; i < basePrices.size(); i++) {
            double spread = basePrices.get(i).getPrice() - competitivePrices.get(i).getPrice();

            if(spread > highestSpread)
            highestSpread = spread;
        }

        return highestSpread;
    }

    private List<HourStatisticsEntity> createHourStatisticsEntities() {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();

        List<String> exchanges = Arrays.asList("Binance", "Coinbase");
        List<String> currencyPairs = Arrays.asList("BTCEUR", "BTCUSDT");

        List<HourStatisticsEntity> entities = new ArrayList<>();

        for (String exchange : exchanges) {
            for (String currencyPair : currencyPairs) {
                double average = calculateAverage(exchange, currencyPair);
                double highestSpread = calculateHighestSpread(exchange, getOtherExchange(exchange), currencyPair);

                HourStatisticsEntity entity = HourStatisticsEntity.builder()
                        .timestamp(timestamp)
                        .currencyPair(currencyPair)
                        .exchange(exchange)
                        .average(average)
                        .highestSpread(highestSpread)
                        .build();

                entities.add(entity);
            }
        }

        return entities;
    }

    private String mapEntityToJson(HourStatisticsEntity hourStatisticsEntity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(hourStatisticsEntity);
    }

    private String getOtherExchange(String exchange) {
        return exchange.equals("Binance") ? "Coinbase" : "Binance";
    }


    public void run() {
        List<HourStatisticsEntity> statisticsEntities = createHourStatisticsEntities();

        log.info("Saving hour statistics in database...");

        for(HourStatisticsEntity entity : statisticsEntities) {
            hourStatisticsRepository.save(entity);
        }
    }
}
