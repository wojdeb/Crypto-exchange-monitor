package com.cemonitor.repositories;

import com.cemonitor.model.HourStatisticsEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Wojciech Dębski
 * @date 17/03/2023
 **/

public interface HourStatisticsRepository extends CrudRepository<HourStatisticsEntity, Long> {
}
