package de.telekom.test.bddwebapp.taxi.controller.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Data
public class ReservationVO {

    @NotBlank
    private String date;

    @NotBlank
    private String departure;

    @NotBlank
    private String earliestStartTime;

    @NotBlank
    private String destination;

    @NotBlank
    private String latestStartTime;

    private String message;

}
