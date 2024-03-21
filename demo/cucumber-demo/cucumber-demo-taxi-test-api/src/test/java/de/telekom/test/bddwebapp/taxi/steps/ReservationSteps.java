package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.taxi.steps.testdata.ReservationPriceVO;
import de.telekom.test.bddwebapp.taxi.steps.testdata.ReservationVO;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class ReservationSteps extends AbstractTaxiSteps {

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    private String taxiAppUrl;

    @Value("${reservation-api-sim.url:http://localhost:6000/reservation-api-sim}")
    private String testDataSimUrl;

    public void theReservationIsDeletedInTheSimulator() {
        testDataSimRequest()
                .when()
                .delete("/testdata/reservation")
                .then()
                .statusCode(200);
    }

    @Given("reservation between {} and {}")
    public void exampleReservation(String earliestStartTime, String latestStartTime) {
        ReservationVO reservation = testDataSimRequest()
                .given()
                .queryParam("earliestStartTime", earliestStartTime)
                .queryParam("latestStartTime", latestStartTime)
                .when()
                .post("/testdata/reservation")
                .then()
                .statusCode(200)
                .extract().as(ReservationVO.class);
        storyInteraction.remember("reservation", reservation);
    }

    @Given("the price is {} € with {} other passengers between {} and {}")
    public void thePriceIsWithOtherPassengers(String price, String passengers, String startTime, String endTime) {
        ReservationPriceVO reservationPrice = new ReservationPriceVO();
        reservationPrice.setPrice(price);
        reservationPrice.setPassengers(passengers);
        reservationPrice.setStartTime(startTime);
        reservationPrice.setEndTime(endTime);

        testDataSimRequest()
                .given()
                .body(reservationPrice)
                .when()
                .put("/testdata/prices")
                .then()
                .statusCode(200);
    }

}
