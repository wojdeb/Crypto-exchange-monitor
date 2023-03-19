package com.cemonitor.repositories;

import com.cemonitor.model.LivePrices;
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
}
