package de.telekom.steps;

import de.telekom.pages.ReservationPage;
import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.assertTrue;

@Steps
public class ReservationSteps extends SeleniumSteps {

    @Given("als Startort ist $departure angegeben")
    public void theDepartureIs(String departure) {
        ReservationPage reservationPage = getCurrentPage();
        reservationPage.setDeparture(departure);
    }

    @Given("als Zielort ist $destination angegeben")
    public void theDestinationPointIs(String destination) {
        ReservationPage reservationPage = getCurrentPage();
        reservationPage.setDestination(destination);
    }

    @When("ein Sammeltaxi zwischen $startTime und $endTime Uhr reserviert wird")
    public void aSharedTaxiIsReservedBetween(String startTime, String endTime) {
        ReservationPage reservationPage = getCurrentPage();
        reservationPage.setStartTime(startTime);
        reservationPage.setEndTime(endTime);
        reservationPage.submitReservation();
    }

    @Then("ist die Reservierung erfolgreich")
    public void theReservationIsSuccessful() {
        ReservationPage reservationPage = getCurrentPage();
        assertTrue(reservationPage.isReservationSuccess());
    }

    @Then("der Preis beträgt $price € zwischen $startTime und $endTime Uhr")
    public void thePriceIsBetweenAnd(String price, String startTime, String endTime) {

    }

}
