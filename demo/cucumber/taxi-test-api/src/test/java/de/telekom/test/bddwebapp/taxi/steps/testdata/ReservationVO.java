package de.telekom.test.bddwebapp.taxi.steps.testdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationVO {

    private String date;
    private String departure;
    private String earliestStartTime;
    private String destination;
    private String latestStartTime;

    private List<ReservationPriceVO> reservationPrices = new ArrayList<>();

}
