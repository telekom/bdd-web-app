package de.telekom.test.bddwebapp.testdata.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Data
@EqualsAndHashCode
public class ReservationVO {

    private String date;
    private String departure;
    private String earliestStartTime;
    private String destination;
    private String latestStartTime;

    private List<ReservationPriceVO> reservationPrices = new ArrayList<>();

}
