package de.telekom.test.bddwebapp.taxi.steps.testdata;

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
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
    private StoryInteraction storyInteraction;

    public ReservationVO exampleReservation(String earliestStartTime, String latestStartTime) {
        ReservationVO reservation = new ReservationVO();
        Date tomorrow = new Date(new Date().getTime() + 86400000l);
        reservation.setDate(new SimpleDateFormat("dd.MM.yyyy").format(tomorrow));
        reservation.setDeparture("Alexanderplatz, Berlin");
        reservation.setDestination("Flughafen Berlin-Tegel");
        reservation.setEarliestStartTime(earliestStartTime);
        reservation.setLatestStartTime(latestStartTime);
        storyInteraction.remember("reservation", reservation);
        return reservation;
    }

    public ReservationPriceVO examplePrice(String price, String passengers) {
        ReservationVO reservation = storyInteraction.recallNotNull("reservation");
        return examplePrice(price, passengers,
                reservation.getEarliestStartTime(),
                reservation.getLatestStartTime());
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
