package de.telekom.test.bddwebapp.taxi.steps.testdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationPriceVO {

    private String startTime;
    private String endTime;
    private String price;
    private String passengers;

}
