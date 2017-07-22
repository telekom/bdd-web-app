package org.jbehave.webapp.taxi.steps;

import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.webapp.api.RequestBuilder;
import org.jbehave.webapp.frontend.steps.SeleniumSteps;
import org.jbehave.webapp.steps.Steps;
import org.jbehave.webapp.taxi.pages.RegistrationPage;
import org.jbehave.webapp.taxi.pages.ReservationPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.from;
import static java.time.format.DateTimeFormatter.ofPattern;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;


@Steps
public class ReservationSteps extends SeleniumSteps {

    @Autowired
    private RequestBuilder requestBuilder;

    @BeforeStory
    public void theReservationIsDeletedInTheSimulator() {
        request().delete("/simulator/config/reservation");
    }

    @Given("ist eine valide Reservierung zwischen $earliestStartTime und $latestStartTime Uhr")
    public void validReservation(String earliestStartTime, String latestStartTime) {
        Date tomorrow = new Date(new Date().getTime() + 86400000l);
        scenarioInteraction.remember("date", new SimpleDateFormat("dd.MM.yyyy").format(tomorrow));
        scenarioInteraction.remember("departure", "Alexanderplatz, Berlin");
        scenarioInteraction.remember("destination", "Flughafen Berlin-Tegel");
        scenarioInteraction.remember("earliestStartTime", earliestStartTime);
        scenarioInteraction.remember("latestStartTime", latestStartTime);
    }

    @Given("zwischen $startTime Uhr und $endTime Uhr ist der Preis $price € bei $passengers Mitfahrern")
    public void betweenStartTimeAndEndTimeThePriceIs(String startTime, String endTime, String price, String passengers) {
        Map<String, String> reservationPrice = new HashMap<>();
        reservationPrice.put("startTime", startTime);
        reservationPrice.put("endTime", endTime);
        reservationPrice.put("price", price);
        reservationPrice.put("passengers", passengers);
        scenarioInteraction.rememberToList("reservationPrices", reservationPrice);
    }

    @When("der Nutzer die Reservierungsseite öffnet")
    private void theUserOpenTheReservationPage() {
        open(getUrlWithHost("localhost:8080", "", ReservationPage.URL));
    }

    @When("die Reservierung im Simulator hinterlegt wird")
    public void theReservationIsSetInTheSimulator() {
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("date", scenarioInteraction.recall("date"));
        reservation.put("departure", scenarioInteraction.recall("departure"));
        reservation.put("destination", scenarioInteraction.recall("destination"));
        reservation.put("earliestStartTime", scenarioInteraction.recall("earliestStartTime"));
        reservation.put("latestStartTime", scenarioInteraction.recall("latestStartTime"));
        body.put("reservation", reservation);
        body.put("reservationPrices", scenarioInteraction.recallList("reservationPrices"));
        request().body(body).post("/simulator/config/reservation");
    }

    @When("die Reservierung im Simulator aktualisiert wird")
    public void theReservationIsUpdateInTheSimulator() {
        theReservationIsDeletedInTheSimulator();
        theSimulatorReturnsSuccessMessage();
        theReservationIsSetInTheSimulator();
        theSimulatorReturnsSuccessMessage();
    }

    @When("ein Sammeltaxi reserviert wird")
    public void aSharedTaxiIsReservedBetween() {
        ReservationPage reservationPage = getCurrentPage();
        reservationPage.setDate(scenarioInteraction.recall("date"));
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

    @Then("zwischen $startTime und $endTime Uhr beträgt der Preis $price bei $passengers Mitfahrern")
    public void thePriceIsBetweenAnd(String startTime, String endTime, String price, String passengers) {
        ReservationPage reservationPage = getCurrentPage();
        String currentPrice = reservationPage.getPriceBetweenStartAndEndTime(startTime, endTime, passengers);
        assertNotNull(currentPrice);
        assertThat(currentPrice, is(price));
    }

    private RequestBuilder request() {
        return requestBuilder.requestWithJsonConfig("http://localhost:8080", "", null, null);
    }

}
