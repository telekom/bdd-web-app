package de.telekom.test.bddwebapp.testdata.controller;

import de.telekom.test.bddwebapp.testdata.config.ReservationSimulatorConfig;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationEventVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceEventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

/**
 * This is a Service-Virtualization for a possible Reservation-API
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RestController
@RequestMapping("api")
public class ReservationApiController {

    @Autowired
    private ReservationSimulatorConfig reservationSimulatorConfig;

    @PostMapping("reservation")
    public Flux<ReservationPriceEventVO> reservation(@Valid ReservationEventVO reservation) {
        ReservationEventVO reserve = reservationSimulatorConfig.reserve(reservation);
        return Flux.fromIterable(reserve.getReservationPrices());
    }

}