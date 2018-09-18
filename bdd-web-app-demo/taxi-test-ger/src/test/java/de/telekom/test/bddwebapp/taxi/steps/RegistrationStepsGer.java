package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Steps
public class RegistrationStepsGer extends RegistrationSteps {

    @Given("die geöffnete Registrierungsseite")
    public void theOpenRegistrationPage() {
        super.theOpenRegistrationPage();
    }

    @Given("ein registrierter Nutzer als $testobject")
    public void registeredUser(String testobject) {
        super.registeredUser(testobject);
    }

    @When("der Nutzer die Registrierung durchführt $testData")
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
