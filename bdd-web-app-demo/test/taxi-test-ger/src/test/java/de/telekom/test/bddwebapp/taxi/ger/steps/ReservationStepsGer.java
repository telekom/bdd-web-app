package de.telekom.test.bddwebapp.taxi.ger.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.steps.ReservationSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;


/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class ReservationStepsGer extends ReservationSteps {

    @Given("eine beispielhafte Reservierung zwischen $earliestStartTime Uhr und $latestStartTime Uhr")
    public void exampleReservation(String earliestStartTime, String latestStartTime) {
        super.exampleReservation(earliestStartTime, latestStartTime);
    }

    @Given("zwischen $startTime Uhr und $endTime Uhr beträgt der Preis $price € bei $passengers Mitfahrern")
    public void thePriceIsWithOtherPassengers(String startTime, String endTime, String price, String passengers) {
        super.thePriceIsWithOtherPassengers(price, passengers, startTime, endTime);
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
