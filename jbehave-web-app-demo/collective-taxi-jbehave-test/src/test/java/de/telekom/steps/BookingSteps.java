package de.telekom.steps;

import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

@Steps
public class BookingSteps extends SeleniumSteps {

    @When("die Reservierung zwischen $startTime und $endTime gebucht wird")
    public void theReservationBetweenIsBooked(String startTime, String endTime) {

    }

    @Then("erhält der Kunde eine Buchungsbestätigung")
    public void theCustomerReceivesBookingConfirmation() {

    }

}
