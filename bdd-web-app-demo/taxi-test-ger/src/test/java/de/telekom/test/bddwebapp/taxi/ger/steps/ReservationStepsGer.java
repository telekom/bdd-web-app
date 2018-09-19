package de.telekom.test.bddwebapp.taxi.ger.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.steps.ReservationSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;


/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Steps
public class ReservationStepsGer extends ReservationSteps {

    @Given("eine valide Reservierung zwischen $earliestStartTime Uhr und $latestStartTime Uhr")
    public void possibleReservation(String earliestStartTime, String latestStartTime) {
        super.possibleReservation(earliestStartTime, latestStartTime);
    }

    @Given("zwischen $startTime Uhr und $endTime Uhr beträgt der Preis $price € bei $passengers Mitfahrern")
    public void betweenStartTimeAndEndTimeThePriceIs(String startTime, String endTime, String price, String passengers) {
        super.betweenStartTimeAndEndTimeThePriceIs(startTime, endTime, price, passengers);
    }

    @Given("die bereits getätigte Reservierung")
    public void reservationAlreadyMade() {
        super.reservationAlreadyMade();
    }

    @When("der Nutzer die Reservierungsseite öffnet")
    public void theUserOpenTheReservationPage() {
        super.theUserOpenTheReservationPage();
    }

    @When("ein Sammeltaxi reserviert wird")
    public void aSharedTaxiIsReservedBetween() {
        super.aSharedTaxiIsReservedBetween();
    }

    @Then("wird die Reservierungsseite angezeigt")
    public void theReservationPageIsShown() {
        super.theReservationPageIsShown();
    }

    @Then("ist die Reservierung erfolgreich")
    public void theReservationIsSuccessful() {
        super.theReservationIsSuccessful();
    }

    @Then("ist die Reservierung nicht erfolgreich")
    public void theReservationIsNotSuccessful() {
        super.theReservationIsNotSuccessful();
    }

    @Then("zwischen $startTime und $endTime Uhr beträgt der Preis $price bei $passengers Mitfahrern")
    public void thePriceIsBetweenAnd(String startTime, String endTime, String price, String passengers) {
        super.thePriceIsBetweenAnd(startTime, endTime, price, passengers);
    }

}
