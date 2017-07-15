package de.telekom.steps;

import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

@Steps
public class RegistrationSteps extends SeleniumSteps {

    @Given("ist ein registrierter Anwender")
    public void registeredUser(){

    }

    @Then("öffnet sich die Registrierungsseite")
    public void theRegistrationPageOpens() {

    }

    @Then("ist der Anwender registriert")
    public void theUserIsRegistered() {

    }

    @When("der Nutzer die Registrierung erfolgreich durchführt")
    public void theUserSuccessfullyCompletedTheRegistration() {

    }

}
