package com.cemonitor.services.db;

import com.cemonitor.model.HourStatistics;
import com.cemonitor.model.LivePrices;
import com.cemonitor.repositories.HourStatisticsRepository;
import com.cemonitor.repositories.LivePricesRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Wojciech Dębski
 * @date 15/03/2023
 **/

@Slf4j
@AllArgsConstructor
public class HourlyStatisticsCalculationService {
    private LivePricesRepository livePricesRepository;
    private HourStatisticsRepository hourStatisticsRepository;

    private double calculateAverage(String exchange, String currencyPair) {
        List<LivePrices> prices = livePricesRepository.findAllByExchangeAndCurrencyPair(exchange, currencyPair);

        return prices.stream().mapToDouble(i -> i.getPrice()).sum() / prices.size();
    }

    private double calculateHighestSpread(String baseExchange, String competiveExchange, String currencyPair) {
        List<LivePrices> basePrices = livePricesRepository.findAllByExchangeAndCurrencyPair(baseExchange, currencyPair);
        List<LivePrices> competivePrices = livePricesRepository.findAllByExchangeAndCurrencyPair(competiveExchange, currencyPair);
        double highestSpread = 0;

        log.info("Calculating highest spread per hour...");

        for(int i = 0; i < basePrices.size(); i++) {
            double spread = basePrices.get(i).getPrice() - competivePrices.get(i).getPrice();

            if(spread > highestSpread)
            highestSpread = spread;
        }

        return highestSpread;
    }

    private List<HourStatistics> createHourStatisticsEntities() {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();

        List<String> exchanges = Arrays.asList("Binance", "Coinbase");
        List<String> currencyPairs = Arrays.asList("BTCEUR", "BTCUSDT");

        List<HourStatistics> entities = new ArrayList<>();

        for (String exchange : exchanges) {
            for (String currencyPair : currencyPairs) {
                double average = calculateAverage(exchange, currencyPair);
                double highestSpread = calculateHighestSpread(exchange, getOtherExchange(exchange), currencyPair);

                HourStatistics entity = HourStatistics.builder()
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

    private String getOtherExchange(String exchange) {
        return exchange.equals("Binance") ? "Coinbase" : "Binance";
    }


    public void run() {
        List<HourStatistics> hourStatisticsEntities = createHourStatisticsEntities();

        log.info("Saving hour statistics in database...");

        for(HourStatistics entity : hourStatisticsEntities) {
            hourStatisticsRepository.save(entity);
        }
    }
}
