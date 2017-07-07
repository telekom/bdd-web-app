package de.telekom.steps;

import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

@Steps
public class ReservationSteps {

    @Given("als Startort ist $place angegeben")
    public void theStartingPointIs(String place) {

    }

    @Given("als Zielort ist $place angegeben")
    public void theDestinationPointIs(String place) {

    }

    @When("ein Sammeltaxi zwischen $startTime und $endTime Uhr reserviert wird")
    public void aSharedTaxiIsReservedBetween(String startTime, String endTime) {

    }

    @Then("ist die Reservierung erfolgreich")
    public void theReservationIsSuccessful() {

    }

    @Then("der Preis beträgt $price €")
    public void thePriceIs(String price) {

    }

    @Then("der Preis beträgt $price € zwischen $startTime und $endTime Uhr")
    public void thePriceIsBetweenAnd(String price, String startTime, String endTime) {

    }

}
