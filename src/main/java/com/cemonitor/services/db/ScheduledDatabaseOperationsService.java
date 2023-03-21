package com.cemonitor.services.db;

import com.cemonitor.repositories.HourStatisticsRepository;
import com.cemonitor.repositories.LivePricesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Wojciech Dębski
 * @date 05/03/2023
 **/

@Slf4j
@Service
public class ScheduledDatabaseOperationsService implements CommandLineRunner {
    private final LivePricesRepository livePricesRepository;
    private final HourStatisticsRepository hourStatisticsRepository;
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private HourStatisticsCalculationService hourService;


    public ScheduledDatabaseOperationsService(LivePricesRepository livePricesRepository, HourStatisticsRepository hourStatisticsRepository) {
        this.livePricesRepository = livePricesRepository;
        this.hourStatisticsRepository = hourStatisticsRepository;
        hourService = new HourStatisticsCalculationService(livePricesRepository, hourStatisticsRepository);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Scheduled operations service started!");

        ScheduledFuture<?> task = executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                hourService.run();
                log.info("Removing all records in LivePrices database...");
                livePricesRepository.deleteAll();
            }
        }, TimeUnit.HOURS.toSeconds(1), TimeUnit.HOURS.toSeconds(1), TimeUnit.SECONDS);
    }
}
