package de.telekom.test.bddwebapp.testdata.simulator;

import de.telekom.test.bddwebapp.testdata.simulator.vo.ReservationConfigVO;
import de.telekom.test.bddwebapp.testdata.simulator.vo.ReservationPricesVO;
import de.telekom.test.bddwebapp.testdata.simulator.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RestController
public class ReservationSimulatorController {

    @Autowired
    private ReservationSimulatorConfig reservationSimulatorConfig;

    @PostMapping("/simulator/api/reservation")
    public ReservationPricesVO reservation(
            @Valid @RequestBody ReservationVO reservation) {
        ReservationPricesVO reservationPrices = reservationSimulatorConfig.reserve(reservation);
        return reservationPrices;
    }

    @PutMapping("/simulator/config/reservation")
    public void reservationPrice(
            @Valid @RequestBody ReservationConfigVO reservationConfig) {
        reservationSimulatorConfig.addOrUpdateReservationConfig(reservationConfig);
    }

    @DeleteMapping("/simulator/config/reservation")
    public void reservationPrice() {
        reservationSimulatorConfig.clearReservationConfigs();
    }

}