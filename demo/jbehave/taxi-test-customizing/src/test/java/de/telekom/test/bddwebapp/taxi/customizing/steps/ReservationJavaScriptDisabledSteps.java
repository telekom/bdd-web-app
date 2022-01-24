package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.jbehave.steps.Steps;
import de.telekom.test.bddwebapp.taxi.pages.ReservationPage;
import de.telekom.test.bddwebapp.taxi.steps.AbstractTaxiSteps;
import org.jbehave.core.annotations.Then;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class ReservationJavaScriptDisabledSteps extends AbstractTaxiSteps {

    @Then("the reservation is not possible because java script is disabled")
    public void theReservationIsNotSuccessful() {
        ReservationPage reservationPage = getCurrentPage();
        assertTrue(reservationPage.javaScriptIsDisabled());
    }

}
