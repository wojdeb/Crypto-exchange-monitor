package com.cemonitor.services.ws;

import com.cemonitor.model.LivePrices;
import com.cemonitor.model.HourStatisticsEntity;
import com.cemonitor.repositories.LivePricesRepository;
import com.cemonitor.websocket.DataWebSocketHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wojciech Dębski
 * @date 10/06/2023
 **/

@Service
@EnableScheduling
@Slf4j
@AllArgsConstructor
public class WebSocketService implements CommandLineRunner {
    private final DataWebSocketHandler dataWebSocketHandler;
    private LivePricesRepository livePricesRepository;

    private List<String> createEntities() {
        List<String> jsonList = new ArrayList<>();
        List<String> currencyPairs = Arrays.asList("BTCEUR", "BTCUSDT");
        List<String> exchanges = Arrays.asList("Binance", "Coinbase");

        for (String currencyPair : currencyPairs) {
            for (String exchange : exchanges) {
                List<LivePrices> livePrice = livePricesRepository.findLastByCurrencyPairAndExchange(currencyPair, exchange);
                String jsonData = convertLivePriceToJson(livePrice);
                dataWebSocketHandler.sendDataToClients(jsonData);
                jsonList.add(jsonData);
            }
        }

        return jsonList;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> jsonList = createEntities();
        log.info("Results: {}", jsonList);
    }

    private String convertLivePriceToJson(List<LivePrices> livePricesList) {
        List<String> jsonList = convertLivePriceToJsonList(livePricesList);
        return String.join(",", jsonList);
    }

    private List<String> convertLivePriceToJsonList(List<LivePrices> livePricesList) {
        List<String> jsonList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for (LivePrices livePrice : livePricesList) {
                String jsonData = objectMapper.writeValueAsString(livePrice);
                jsonList.add(jsonData);
            }
        } catch (JsonProcessingException e) {
            log.error("Error converting LivePrices to JSON: {}", e.getMessage());
        }
        return jsonList;
    }

    @Scheduled(fixedRate = 5000)
    public void sendDataToClientsPeriodically() {
        List<String> jsonList = createEntities();
        log.info("Results: {}", jsonList);
    }
}