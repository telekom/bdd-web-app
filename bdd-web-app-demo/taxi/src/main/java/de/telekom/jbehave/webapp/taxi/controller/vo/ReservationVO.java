package de.telekom.jbehave.webapp.taxi.controller.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
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

}
