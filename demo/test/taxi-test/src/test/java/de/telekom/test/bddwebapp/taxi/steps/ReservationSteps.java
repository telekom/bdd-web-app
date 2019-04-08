package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.pages.ReservationPage;
import de.telekom.test.bddwebapp.taxi.steps.testdata.ReservationTestData;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
@Steps
public class ReservationSteps extends AbstractTaxiSteps {

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    private String taxiAppUrl;

    @Value("${testdata-sim.url:http://localhost:6000/testdata-sim}")
    private String testDataSimUrl;

    @Autowired
    private ReservationTestData reservationTestData;

    @BeforeStory
    public void theReservationIsDeletedInTheSimulator() {
        testDataSimRequest()
                .when()
                .delete("/testdata/reservation")
                .then()
                .statusCode(200);
    }

    @Given("possible reservation between $earliestStartTime and $latestStartTime")
    public void possibleReservation(String earliestStartTime, String latestStartTime) {
        testDataSimRequest()
                .given()
                .body(reservationTestData.exampleReservation(earliestStartTime, latestStartTime))
                .when()
                .put("/testdata/reservation")
                .then()
                .statusCode(200);
    }

    @Given("the price is $price € with $passengers other passengers")
    public void thePriceIsWithOtherPassengers(String price, String passengers) {
        testDataSimRequest()
                .given()
                .body(reservationTestData.examplePrice(price, passengers))
                .when()
                .put()
                .then()
                .statusCode(200);
    }

    @Given("the price is $price € with $passengers other passengers between $earliestStartTime and $latestStartTime")
    public void thePriceIsWithOtherPassengers(String price, String passengers, String earliestStartTime, String latestStartTime) {
        testDataSimRequest()
                .given()
                .body(reservationTestData.examplePrice(price, passengers, earliestStartTime, latestStartTime))
                .when()
                .put()
                .then()
                .statusCode(200);
    }

    @When("the user open the reservation page")
    public void theUserOpenTheReservationPage() {
        open(appendUrl(taxiAppUrl, ReservationPage.URL));
    }

    @When("reserve a shared taxi")
    public void aSharedTaxiIsReservedBetween() {
        ReservationPage reservationPage = getCurrentPage();
        reservationPage.setDate(scenarioInteraction.recallNotNull("date"));
        reservationPage.setDeparture(scenarioInteraction.recallNotNull("departure"));
        reservationPage.setDestination(scenarioInteraction.recallNotNull("destination"));
        reservationPage.setEarliestStartTime(scenarioInteraction.recallNotNull("earliestStartTime"));
        reservationPage.setLatestStartTime(scenarioInteraction.recallNotNull("latestStartTime"));
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

    @Then("between $startTime and $endTime the price is $price at $passengers passengers")
    public void thePriceIsBetweenAnd(String startTime, String endTime, String price, String passengers) {
        ReservationPage reservationPage = getCurrentPage();
        String currentPrice = reservationPage.getPriceBetweenStartAndEndTime(startTime, endTime, passengers);
        assertNotNull(currentPrice);
        assertThat(currentPrice, is(price));
    }

}
