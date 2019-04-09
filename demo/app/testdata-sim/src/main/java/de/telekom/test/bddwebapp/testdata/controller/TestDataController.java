package de.telekom.test.bddwebapp.testdata.controller;

import de.telekom.test.bddwebapp.testdata.builder.TestDataBuilder;
import de.telekom.test.bddwebapp.testdata.config.ReservationSimulatorConfig;
import de.telekom.test.bddwebapp.testdata.controller.vo.RegistrationVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Manage the lifecycle of test data.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RestController
@RequestMapping("testdata")
public class TestDataController {

    @Autowired
    private TestDataBuilder testDataBuilder;

    @Autowired
    private ReservationSimulatorConfig reservationSimulatorConfig;

    @PostMapping("user")
    public RegistrationVO createNewUser() {
        return testDataBuilder.createNewUser();
    }

    @PostMapping("reservation")
    public ReservationVO createExampleReservation(@RequestParam String earliestStartTime, @RequestParam String latestStartTime) {
        ReservationVO reservation = testDataBuilder.createExampleReservation(earliestStartTime, latestStartTime);
        reservationSimulatorConfig.setCurrentReservation(reservation);
        return reservationSimulatorConfig.getCurrentReservation();
    }

    @DeleteMapping("reservation")
    public void deletePossibleReservation() {
        reservationSimulatorConfig.clearReservation();
    }

    @PutMapping("prices")
    public void updateOfferedPrice(@RequestBody ReservationPriceVO reservationPrice) {
        reservationSimulatorConfig.updatePrice(reservationPrice);
    }

}