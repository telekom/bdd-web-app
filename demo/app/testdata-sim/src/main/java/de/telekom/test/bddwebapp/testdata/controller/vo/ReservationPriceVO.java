package de.telekom.test.bddwebapp.testdata.controller.vo;

import lombok.Data;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Data
public class ReservationPriceVO {

    private String startTime;
    private String endTime;
    private String price;
    private String passengers;

}
