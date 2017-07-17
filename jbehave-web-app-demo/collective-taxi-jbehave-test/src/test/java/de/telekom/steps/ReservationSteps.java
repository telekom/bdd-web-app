package de.telekom.steps;

import de.telekom.pages.ReservationPage;
import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@Steps
public class ReservationSteps extends SeleniumSteps {

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

}
