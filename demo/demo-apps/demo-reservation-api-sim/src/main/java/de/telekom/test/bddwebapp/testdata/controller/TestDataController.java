package de.telekom.test.bddwebapp.testdata.controller;

import de.telekom.test.bddwebapp.testdata.config.ReservationSimulatorConfig;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationEventVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceEntryVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceEventVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Manage the lifecycle of test data.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RestController
@RequestMapping("testdata")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestDataController {

    private final ReservationSimulatorConfig reservationSimulatorConfig;

    @PostMapping("reservation")
    public ReservationEventVO createExampleReservation(@RequestParam String earliestStartTime, @RequestParam String latestStartTime) {
        var reservation = new ReservationEventVO();
        var tomorrow = new Date(new Date().getTime() + 86400000L);
        reservation.setDate(new SimpleDateFormat("dd.MM.yyyy").format(tomorrow));
        reservation.setDeparture("Alexanderplatz, Berlin");
        reservation.setDestination("Flughafen Berlin-Tegel");
        reservation.setEarliestStartTime(earliestStartTime);
        reservation.setLatestStartTime(latestStartTime);
        reservation.setReservationPriceEvent(new ReservationPriceEventVO("Update reservation price event"));

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