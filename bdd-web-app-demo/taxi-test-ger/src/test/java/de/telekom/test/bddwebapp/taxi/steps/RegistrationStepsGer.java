package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

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

    @Given("ein registrierter Nutzer")
    public void registeredUser() {
        super.registeredUser();
    }

    @Given("gültige Registrierungsdaten")
    public void validRegistrationDataForUser() {
        super.validRegistrationDataForUser();
    }

    @Given("gültige Registrierungsdaten mit Kontrollflussfehler in der Anwendung")
    public void validRegistrationDataWithErrorForUser() {
        super.validRegistrationDataWithErrorForUser();
    }

    @Given("ungültige Registrierungsdaten")
    public void invalidRegistrationDataForUser() {
        super.invalidRegistrationDataForUser();
    }

    @When("der Nutzer die Registrierung durchführt")
    public void theUserSuccessfullyCompletedTheRegistration() {
        super.theUserSuccessfullyCompletedTheRegistration();
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
