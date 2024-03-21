package de.telekom.test.bddwebapp.testdata.controller.vo;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Getter
public class ReservationPriceEventVO {

    public static final ReservationPriceEventVO NO_PRICES_YET = new ReservationPriceEventVO("No prices yet!");

    private final String message;

    private final Date date;

    private final List<ReservationPriceEntryVO> reservationPrices = new ArrayList<>();

    public ReservationPriceEventVO(String message) {
        this.message = message;
        this.date = new Date();
    }

}
