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
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${hostIncludingPort}")
    private String hostIncludingPort;

    @Autowired
    private RequestBuilder requestBuilder;

    @BeforeStory
    public void theReservationIsDeletedInTheSimulator() {
        request().delete("/simulator/config/reservation");
    }

    @Given("eine valide Reservierung")
    public void possibleReservation() {
        Date tomorrow = new Date(new Date().getTime() + 86400000l);
        scenarioInteraction.remember("date", new SimpleDateFormat("dd.MM.yyyy").format(tomorrow));
        scenarioInteraction.remember("departure", "Alexanderplatz, Berlin");
        scenarioInteraction.remember("destination", "Flughafen Berlin-Tegel");
        scenarioInteraction.remember("earliestStartTime", "10:00");
        scenarioInteraction.remember("latestStartTime", "10:30");
    }

    @Given("zwischen $startTime Uhr und $endTime Uhr beträgt der Preis $price € bei $passengers Mitfahrern")
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
        request().body(body).put("/simulator/config/reservation");
        requestBuilder.response().then().statusCode(200);
    }

    @Given("die bereits getätigte Reservierung")
    public void theReservationAlreadyMade(){
        scenarioInteraction.rememberFromStoryInteraction("date");
        scenarioInteraction.rememberFromStoryInteraction("departure");
        scenarioInteraction.rememberFromStoryInteraction("destination");
        scenarioInteraction.rememberFromStoryInteraction("earliestStartTime");
        scenarioInteraction.rememberFromStoryInteraction("latestStartTime");
    }

    @When("der Nutzer die Reservierungsseite öffnet")
    public void theUserOpenTheReservationPage() {
        open(getUrlWithHost(hostIncludingPort, ReservationPage.URL));
    }

    @When("ein Sammeltaxi reserviert wird")
    public void aSharedTaxiIsReservedBetween() {
        ReservationPage reservationPage = getCurrentPage();
        reservationPage.setDate(scenarioInteraction.recallNotNull("date"));
        reservationPage.setDeparture(scenarioInteraction.recallNotNull("departure"));
        reservationPage.setDestination(scenarioInteraction.recallNotNull("destination"));
        reservationPage.setEarliestStartTime(scenarioInteraction.recallNotNull("earliestStartTime"));
        reservationPage.setLatestStartTime(scenarioInteraction.recallNotNull("latestStartTime"));
        reservationPage.submitReservation();
    }

    @Then("wird die Reservierungsseite angezeigt")
    public void theReservationPageIsShown() {
        createExpectedPage(ReservationPage.class);
    }

    @Then("ist die Reservierung erfolgreich")
    public void theReservationIsSuccessful() {
        ReservationPage reservationPage = getCurrentPage();
        assertTrue(reservationPage.isReservationSuccess());
        storyInteraction.rememberFromScenarioInteraction("date");
        storyInteraction.rememberFromScenarioInteraction("departure");
        storyInteraction.rememberFromScenarioInteraction("destination");
        storyInteraction.rememberFromScenarioInteraction("earliestStartTime");
        storyInteraction.rememberFromScenarioInteraction("latestStartTime");
    }

    @Then("ist die Reservierung nicht erfolgreich")
    public void theReservationIsNotSuccessful() {
        ReservationPage reservationPage = getCurrentPage();
        assertTrue(reservationPage.isReservationNotPossible());
    }

    @Then("zwischen $startTime und $endTime Uhr beträgt der Preis $price bei $passengers Mitfahrern")
    public void thePriceIsBetweenAnd(String startTime, String endTime, String price, String passengers) {
        ReservationPage reservationPage = getCurrentPage();
        String currentPrice = reservationPage.getPriceBetweenStartAndEndTime(startTime, endTime, passengers);
        assertNotNull(currentPrice);
        assertThat(currentPrice, is(price));
    }

    private RequestBuilder request() {
        return requestBuilder.requestWithJsonConfig(hostIncludingPort, "", null, null);
    }

}
