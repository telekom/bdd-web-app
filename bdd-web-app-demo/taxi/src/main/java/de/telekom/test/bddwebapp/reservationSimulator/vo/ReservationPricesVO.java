package de.telekom.test.bddwebapp.reservationSimulator.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Data
public class ReservationPricesVO {

    private String message;
    private List<ReservationPriceVO> reservationPrices;

}