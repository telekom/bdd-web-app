package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.taxi.pages.ReservationPage;
import de.telekom.test.bddwebapp.taxi.steps.testdata.ReservationPriceVO;
import de.telekom.test.bddwebapp.taxi.steps.testdata.ReservationVO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;

import static de.telekom.test.bddwebapp.frontend.util.UrlAppender.appendUrl;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class ReservationSteps extends AbstractTaxiSteps {

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    private String taxiAppUrl;

    @Value("${testdata-sim.url:http://localhost:6000/testdata-sim}")
    private String testDataSimUrl;

    public void theReservationIsDeletedInTheSimulator() {
        testDataSimRequest()
                .when()
                .delete("/testdata/reservation")
                .then()
                .statusCode(200);
    }

    @Given("example reservation between {} and {}")
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

    @Then("between {} and {} the price is {} at {} passengers")
    public void thePriceIsBetweenAnd(String startTime, String endTime, String price, String passengers) {
        ReservationPage reservationPage = getCurrentPage();
        var reservationPrice = reservationPage.getPriceBetweenStartAndEndTime(startTime, endTime);
        assertTrue(reservationPrice.isPresent());
        assertThat(reservationPrice.get().getPrice(), containsString(price));
        assertThat(reservationPrice.get().getPassengers(), is(passengers));
    }

}
