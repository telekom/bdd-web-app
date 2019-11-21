package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.taxi.pages.ReservationPage;
import de.telekom.test.bddwebapp.taxi.steps.testdata.ReservationPriceVO;
import de.telekom.test.bddwebapp.taxi.steps.testdata.ReservationVO;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;

import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.BEFORE_FEATURE_ORDER;
import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.isBeforeFeature;
import static de.telekom.test.bddwebapp.util.UrlAppender.appendUrl;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;


/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class ReservationSteps extends AbstractTaxiSteps {

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    private String taxiAppUrl;

    @Value("${testdata-sim.url:http://localhost:6000/testdata-sim}")
    private String testDataSimUrl;

    @Before(order = BEFORE_FEATURE_ORDER)
    public void theReservationIsDeletedInTheSimulator() {
        if (isBeforeFeature("theReservationIsDeletedInTheSimulator")) {
            testDataSimRequest()
                    .when()
                    .delete("/testdata/reservation")
                    .then()
                    .statusCode(200);
        }
    }

    @Given("example reservation between {param} and {param}")
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

    @Given("the price is {param} € with {param} other passengers between {param} and {param}")
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

    @When("the user open the reservation page")
    public void theUserOpenTheReservationPage() {
        open(appendUrl(taxiAppUrl, ReservationPage.URL));
    }

    @When("reserve a shared taxi")
    public void aSharedTaxiIsReservedBetween() {
        ReservationVO reservation = storyInteraction.recallNotNull("reservation");
        ReservationPage reservationPage = getCurrentPage();
        reservationPage.setDate(reservation.getDate());
        reservationPage.setDeparture(reservation.getDeparture());
        reservationPage.setDestination(reservation.getDestination());
        reservationPage.setEarliestStartTime(reservation.getEarliestStartTime());
        reservationPage.setLatestStartTime(reservation.getLatestStartTime());
        reservationPage.submitReservation();
    }

    @Then("the reservation page is shown")
    public void theReservationPageIsShown() {
        createExpectedPage(ReservationPage.class);
    }

    @Then("the reservation is successful")
    public void theReservationIsSuccessful() {
        ReservationPage reservationPage = getCurrentPage();
        assertTrue(reservationPage.isReservationSuccess());
    }

    @Then("the reservation is not successful")
    public void theReservationIsNotSuccessful() {
        ReservationPage reservationPage = getCurrentPage();
        assertTrue(reservationPage.isReservationNotPossible());
    }

    @Then("between {param} and {param} the price is {param} at {param} passengers")
    public void thePriceIsBetweenAnd(String startTime, String endTime, String price, String passengers) {
        ReservationPage reservationPage = getCurrentPage();
        String currentPrice = reservationPage.getPriceBetweenStartAndEndTime(startTime, endTime, passengers);
        assertNotNull(currentPrice);
        assertThat(currentPrice, is(price));
    }

}
