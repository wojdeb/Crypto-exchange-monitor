package com.cemonitor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Wojciech Dębski
 * @date 05/03/2023
 **/

@NoArgsConstructor
@Getter
@Setter
@Entity
public class LivePrices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String timestamp;
    private String currencyPair;
    private String exchange;
    private Double price;

    public LivePrices(String timestamp, String currencyPair, String exchange, Double price) {
        this.timestamp = timestamp;
        this.currencyPair = currencyPair;
        this.exchange = exchange;
        this.price = price;
    }
}
