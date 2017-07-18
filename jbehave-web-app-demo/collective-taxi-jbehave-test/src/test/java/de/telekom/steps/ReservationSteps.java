package de.telekom.steps;

import de.telekom.pages.ReservationPage;
import de.telekom.test.api.RequestBuilder;
import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@Steps
public class ReservationSteps extends SeleniumSteps {

    @Autowired
    private RequestBuilder requestBuilder;

    @Autowired
    private RegistrationSteps registrationSteps;

    @Given("ein Kunde der bereits eine Reservierung zwischen $startTime und $endTime Uhr vorgenommen hat")
    public void aCustomerWhoHasAlreadyMadeReservationBetween(String startTime, String endTime) {
        registrationSteps.registeredUser("kunde");
    }

    @Given("ist der Startort $departure")
    public void theDeparture(String departure) {
        scenarioInteraction.remember("departure", departure);
    }

    @Given("ist der Zielort $destination")
    public void theDestination(String destination) {
        scenarioInteraction.remember("destination", destination);
    }

    @Given("ist der früheste Startzeitpunkt $earliestStartTime Uhr")
    public void earliestStartTime(String earliestStartTime) {
        scenarioInteraction.remember("earliestStartTime", earliestStartTime);
    }

    @Given("ist der späteste Startzeitpunkt $latestStartTime Uhr")
    public void latestStartTime(String latestStartTime) {
        scenarioInteraction.remember("latestStartTime", latestStartTime);
    }

    @When("die Reservierung im Simulator hinterlegt wird")
    public void theReservationIsSetInTheSimulator() {
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("departure", scenarioInteraction.recall("departure"));
        reservation.put("destination", scenarioInteraction.recall("destination"));
        reservation.put("earliestStartTime", scenarioInteraction.recall("earliestStartTime"));
        reservation.put("latestStartTime", scenarioInteraction.recall("latestStartTime"));
        body.put("reservation", reservation);
        List<Map<String, Object>> reservationPrices = new ArrayList<>();
        Map<String, Object> reservationPrice = new HashMap<>();
        reservationPrice.put("startTime", "10:00");
        reservationPrice.put("endTime", "12:00");
        reservationPrice.put("price", "15,50");
        reservationPrices.add(reservationPrice);
        body.put("reservationPrices", reservationPrices);
        request().body(body).post("/config/reservation");
    }

    @When("ein Sammeltaxi reserviert wird")
    public void aSharedTaxiIsReservedBetween() {
        ReservationPage reservationPage = getCurrentPage();
        reservationPage.setDeparture(scenarioInteraction.recall("departure"));
        reservationPage.setDestination(scenarioInteraction.recall("destination"));
        reservationPage.setEarliestStartTime(scenarioInteraction.recall("earliestStartTime"));
        reservationPage.setLatestStartTime(scenarioInteraction.recall("latestStartTime"));
        reservationPage.submitReservation();
    }

    @Then("gibt der Simulator eine Erfolgsmeldung zurück")
    public void theSimulatorReturnsSuccessMessage() {
        requestBuilder.response().then().statusCode(200);
    }

    @Then("öffnet sich die Reservierungsseite")
    public void theReservationPageOpens() {
        createExpectedPage(ReservationPage.class);
    }

    @Then("ist die Reservierung erfolgreich")
    public void theReservationIsSuccessful() {
        ReservationPage reservationPage = getCurrentPage();
        assertTrue(reservationPage.isReservationSuccess());
    }

    @Then("der Preis beträgt $price zwischen $startTime und $endTime Uhr")
    public void thePriceIsBetweenAnd(String price, String startTime, String endTime) {
        ReservationPage reservationPage = getCurrentPage();
        String currentPrice = reservationPage.getPriceBetweenStartAndEndTime(startTime, endTime);
        assertNotNull(price);
        assertThat(currentPrice, is(price));
    }

    private RequestBuilder request() {
        return requestBuilder.requestWithJsonConfig("http://localhost:8080", "", null, null);
    }

}
