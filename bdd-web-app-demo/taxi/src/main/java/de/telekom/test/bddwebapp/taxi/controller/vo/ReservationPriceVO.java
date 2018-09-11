package de.telekom.test.bddwebapp.taxi.controller.vo;

import lombok.Data;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Data
public class ReservationPriceVO {

    private String startTime;
    private String endTime;
    private String price;
    private String passengers;

}
