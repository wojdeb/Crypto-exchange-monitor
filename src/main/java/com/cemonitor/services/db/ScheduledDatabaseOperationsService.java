package com.cemonitor.services.db;

import com.cemonitor.repositories.LivePricesRepository;
import org.springframework.boot.CommandLineRunner;

/**
 * @author Wojciech Dębski
 * @date 05/03/2023
 **/

public class ScheduledDatabaseOperationsService implements CommandLineRunner {
    private final LivePricesRepository livePricesRepository;

    public ScheduledDatabaseOperationsService(LivePricesRepository livePricesRepository) {
        this.livePricesRepository = livePricesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //TODO:
        // 1. Implement method to clear "LivePrices" database every hour (temporary solution)
    }


}
