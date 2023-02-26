package de.telekom.test.bddwebapp.testdata.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Data
@EqualsAndHashCode
public class ReservationEventVO {

    private String username;

    private String date;
    private String departure;
    private String earliestStartTime;
    private String destination;
    private String latestStartTime;

    private ReservationPriceEventVO reservationPriceEvent;

}
