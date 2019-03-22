package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.pages.ReservationPage;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;


/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class ReservationSteps extends AbstractTaxiSteps {

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    private String taxiAppUrl;

    @Value("${testdata-sim.url:http://localhost:6000/testdata-sim}")
    private String testDataSimUrl;

    @BeforeStory
    public void theReservationIsDeletedInTheSimulator() {
        testDataSimRequest().delete("/simulator/config/reservation").then().statusCode(200);
    }

    @Given("possible reservation between $earliestStartTime and $latestStartTime")
    public void possibleReservation(String earliestStartTime, String latestStartTime) {
        Date tomorrow = new Date(new Date().getTime() + 86400000l);
        scenarioInteraction.remember("date", new SimpleDateFormat("dd.MM.yyyy").format(tomorrow));
        scenarioInteraction.remember("departure", "Alexanderplatz, Berlin");
        scenarioInteraction.remember("destination", "Flughafen Berlin-Tegel");
        scenarioInteraction.remember("earliestStartTime", earliestStartTime);
        scenarioInteraction.remember("latestStartTime", latestStartTime);
    }

    @Given("between $startTime and $endTime the price is $price € with $passengers passengers")
    public void betweenStartTimeAndEndTimeThePriceIs(String startTime, String endTime, String price, String passengers) {
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("date", scenarioInteraction.recallNotNull("date"));
        reservation.put("departure", scenarioInteraction.recallNotNull("departure"));
        reservation.put("destination", scenarioInteraction.recallNotNull("destination"));
        reservation.put("earliestStartTime", scenarioInteraction.recallNotNull("earliestStartTime"));
        reservation.put("latestStartTime", scenarioInteraction.recallNotNull("latestStartTime"));
        body.put("reservation", reservation);
        Map<String, String> reservationPrice = new HashMap<>();
        reservationPrice.put("startTime", startTime);
        reservationPrice.put("endTime", endTime);
        reservationPrice.put("price", price);
        reservationPrice.put("passengers", passengers);
        scenarioInteraction.rememberToList("reservationPrices", reservationPrice);
        body.put("reservationPrices", scenarioInteraction.recallList("reservationPrices"));
        testDataSimRequest().body(body).put("/simulator/config/reservation");
        recallResponse().then().statusCode(200);
    }

    @Given("reservation already made")
    public void reservationAlreadyMade() {
        scenarioInteraction.rememberFromStoryInteraction("date");
        scenarioInteraction.rememberFromStoryInteraction("departure");
        scenarioInteraction.rememberFromStoryInteraction("destination");
        scenarioInteraction.rememberFromStoryInteraction("earliestStartTime");
        scenarioInteraction.rememberFromStoryInteraction("latestStartTime");
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
        storyInteraction.rememberFromScenarioInteraction("date");
        storyInteraction.rememberFromScenarioInteraction("departure");
        storyInteraction.rememberFromScenarioInteraction("destination");
        storyInteraction.rememberFromScenarioInteraction("earliestStartTime");
        storyInteraction.rememberFromScenarioInteraction("latestStartTime");
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
