package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.taxi.pages.ReservationPage;
import de.telekom.test.bddwebapp.taxi.steps.AbstractTaxiSteps;
import io.cucumber.java.en.Then;

import static junit.framework.TestCase.assertTrue;

public class ReservationJavaScriptDisabledSteps extends AbstractTaxiSteps {

    @Then("the reservation is not possible because java script is disabled")
    public void theReservationIsNotSuccessful() {
        ReservationPage reservationPage = getCurrentPage();
        assertTrue(reservationPage.javaScriptIsDisabled());
    }

}
