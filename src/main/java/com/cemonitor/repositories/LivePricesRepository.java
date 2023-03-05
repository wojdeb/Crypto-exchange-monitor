package com.cemonitor.repositories;

import com.cemonitor.model.LivePrices;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Wojciech Dębski
 * @date 05/03/2023
 **/

public interface LivePricesRepository extends CrudRepository<LivePrices, Long> {
}
