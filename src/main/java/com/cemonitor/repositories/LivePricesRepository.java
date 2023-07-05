package com.cemonitor.repositories;

import com.cemonitor.model.LivePrices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Wojciech Dębski
 * @date 05/03/2023
 **/

public interface LivePricesRepository extends CrudRepository<LivePrices, Long> {
    List<LivePrices> findAllByCurrencyPair(String currencyPair);
    List<LivePrices> findAllByExchange(String exchange);
    List<LivePrices> findAllByExchangeAndCurrencyPair(String exchange, String currencyPair);

    @Query(value = "SELECT top 1 * from LIVE_PRICES where CURRENCY_PAIR= :currencyPair ORDER BY timestamp DESC", nativeQuery = true)
    LivePrices findLastByCurrencyPair(String currencyPair);

    @Query(value = "SELECT top 1 * from LIVE_PRICES where CURRENCY_PAIR= :currencyPair and EXCHANGE= :exchange ORDER BY timestamp DESC", nativeQuery = true)
    List<LivePrices> findLastByCurrencyPairAndExchange(String currencyPair, String exchange);
}
