package de.telekom.test.bddwebapp.taxi.ger.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.steps.RegistrationSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class RegistrationStepsGer extends RegistrationSteps {

    @Given("die geöffnete Registrierungsseite")
    public void theOpenRegistrationPage() {
        super.theOpenRegistrationPage();
    }

    @Given("ein registrierter Nutzer namens $testobject")
    public void registeredUser(String testobject) {
        super.registeredUser(testobject);
    }

    @When("der Nutzer die Registrierung mit folgenden Daten durchführt $testData")
    public void theUserSuccessfullyCompletedTheRegistration(ExamplesTable testData) {
        super.theUserRegister(testData);
    }

    @Then("wird die Registrierungsseite angezeigt")
    public void theRegistrationPageIsShown() {
        super.theRegistrationPageIsShown();
    }

    @Then("der Nutzer erhält die Nachricht, dass die Registrierungsdaten ungültig sind")
    public void theUserReceivesTheMessageThatTheRegistrationDataIsInvalid() {
        super.theUserReceivesTheMessageThatTheRegistrationDataIsInvalid();
    }

}
