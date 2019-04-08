package de.telekom.test.bddwebapp.taxi.steps.testdata;

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.steps.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class ReservationTestData {

    @Autowired
    private ScenarioInteraction scenarioInteraction;

    public ReservationVO exampleReservation(String earliestStartTime, String latestStartTime) {
        ReservationVO reservation = new ReservationVO();
        Date tomorrow = new Date(new Date().getTime() + 86400000l);
        reservation.setDate(new SimpleDateFormat("dd.MM.yyyy").format(tomorrow));
        reservation.setDeparture("Alexanderplatz, Berlin");
        reservation.setDestination("Flughafen Berlin-Tegel");
        reservation.setEarliestStartTime(earliestStartTime);
        reservation.setLatestStartTime(latestStartTime);
        scenarioInteraction.remember("earliestStartTime", earliestStartTime);
        scenarioInteraction.remember("latestStartTime", latestStartTime);
        return reservation;
    }

    public ReservationPriceVO examplePrice(String price, String passengers) {
        return examplePrice(price, passengers,
                scenarioInteraction.recallNotNull("earliestStartTime"),
                scenarioInteraction.recallNotNull("latestStartTime"));
    }

    public ReservationPriceVO examplePrice(String price, String passengers, String startTime, String endTime) {
        ReservationPriceVO reservationPrice = new ReservationPriceVO();
        reservationPrice.setPrice(price);
        reservationPrice.setPassengers(passengers);
        reservationPrice.setStartTime(startTime);
        reservationPrice.setEndTime(endTime);
        return reservationPrice;
    }

}
