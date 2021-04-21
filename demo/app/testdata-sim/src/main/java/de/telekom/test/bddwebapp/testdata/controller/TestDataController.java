package de.telekom.test.bddwebapp.testdata.controller;

import de.telekom.test.bddwebapp.testdata.builder.TestDataBuilder;
import de.telekom.test.bddwebapp.testdata.config.ReservationSimulatorConfig;
import de.telekom.test.bddwebapp.testdata.controller.vo.RegistrationVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationEventVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceEntryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Manage the lifecycle of test data.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RestController
@RequestMapping("testdata")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestDataController {

    private final TestDataBuilder testDataBuilder;
    private final ReservationSimulatorConfig reservationSimulatorConfig;

    @PostMapping("user")
    public RegistrationVO createNewUser() {
        return testDataBuilder.createNewUser();
    }

    @PostMapping("reservation")
    public ReservationEventVO createExampleReservation(@RequestParam String earliestStartTime, @RequestParam String latestStartTime) {
        var reservation = testDataBuilder.createExampleReservation(earliestStartTime, latestStartTime);
        reservationSimulatorConfig.setCurrentReservation(reservation);
        return reservation;
    }

    @DeleteMapping("reservation")
    public void deletePossibleReservation() {
        reservationSimulatorConfig.clearReservation();
    }

    @PutMapping("prices")
    public void updateOfferedPrice(@RequestBody ReservationPriceEntryVO priceEntry) {
        reservationSimulatorConfig.updatePrice(priceEntry);
    }

}