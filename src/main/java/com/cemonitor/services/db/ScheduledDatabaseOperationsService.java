package com.cemonitor.services.db;

import com.cemonitor.repositories.HourStatisticsRepository;
import com.cemonitor.repositories.LivePricesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Wojciech Dębski
 * @date 05/03/2023
 **/

@Slf4j
@Component
public class ScheduledDatabaseOperationsService implements CommandLineRunner {
    private final LivePricesRepository livePricesRepository;
    private final HourStatisticsRepository hourStatisticsRepository;
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private HourlyStatisticsCalculationService hourlyService;


    public ScheduledDatabaseOperationsService(LivePricesRepository livePricesRepository, HourStatisticsRepository hourStatisticsRepository) {
        this.livePricesRepository = livePricesRepository;
        this.hourStatisticsRepository = hourStatisticsRepository;
        hourlyService = new HourlyStatisticsCalculationService(livePricesRepository, hourStatisticsRepository);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Scheduled operations service started!");

        ScheduledFuture<?> task = executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                hourlyService.run();
                log.info("Removing all records in LivePrices database...");
                livePricesRepository.deleteAll();

                //TODO: temporary solution - first 4 records are added just after running application, so they contains null values because livePricesRepository were empty when thread was executed.
                if(hourStatisticsRepository.existsById(1L)) {
                    log.info("Removing first 4 records (with null values)");
                    hourStatisticsRepository.deleteAllById(Arrays.asList(1L, 2L, 3L, 4L));
                }
            }
        }, 0, 60, TimeUnit.MINUTES);
    }
}
