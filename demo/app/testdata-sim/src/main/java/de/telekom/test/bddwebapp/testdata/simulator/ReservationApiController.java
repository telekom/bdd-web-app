package de.telekom.test.bddwebapp.testdata.simulator;

import de.telekom.test.bddwebapp.testdata.simulator.config.ReservationSimulatorConfig;
import de.telekom.test.bddwebapp.testdata.simulator.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * This is a Service-Virtualization for a possible Reservation-API
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RestController("api")
public class ReservationApiController {

    @Autowired
    private ReservationSimulatorConfig reservationSimulatorConfig;

    @PostMapping("reservation")
    public ReservationVO reservation(@Valid @RequestBody ReservationVO reservation) {
        return reservationSimulatorConfig.reserve(reservation);
    }

}