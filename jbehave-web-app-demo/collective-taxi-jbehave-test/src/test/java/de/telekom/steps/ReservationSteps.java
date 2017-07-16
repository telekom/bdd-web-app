package de.telekom.steps;

import de.telekom.pages.ReservationPage;
import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
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

    @Given("ein Kunde der bereits eine Reservierung zwischen $startTime und $endTime Uhr vorgenommen hat")
    public void aCustomerWhoHasAlreadyMadeReservationBetween(String startTime, String endTime){

    }

    @When("ein Sammeltaxi zwischen $startTime und $endTime Uhr reserviert wird")
    public void aSharedTaxiIsReservedBetween(String startTime, String endTime) {
        ReservationPage reservationPage = getCurrentPage();
        reservationPage.setStartTime(startTime);
        reservationPage.setEndTime(endTime);
        reservationPage.submitReservation();
    }

    @Then("öffnet sich die Reservierungsseite")
    public void theReservationPageOpens(){
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
