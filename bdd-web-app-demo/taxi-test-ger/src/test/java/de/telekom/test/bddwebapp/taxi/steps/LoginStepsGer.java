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
public class LoginStepsGer extends LoginSteps {

    @Given("die geöffnete Loginseite")
    public void theOpenedLoginPage() {
        super.theOpenedLoginPage();
    }

    @Given("ein eingeloggter Nutzer $testobject")
    public void loggedInCustomer(String testobject) {
        super.loggedInCustomer(testobject);
    }

    @When("der Nutzer die Startseite öffnet")
    public void theUserOpensTheLoginPage() {
        super.theUserOpensTheLoginPage();
    }

    @When("der Nutzer sich einloggt $username $password")
    public void theUserLogsIn(String username, String password) {
        super.theUserLogsIn(username, password);
    }

    @When("der Nutzer den Link zur Registrierung anklickt")
    public void userClicksTheLinkToTheRegistration() {
        super.userClicksTheLinkToTheRegistration();
    }

    @Then("wird die Loginseite angezeigt")
    public void theLoginPageIsShown() {
        super.theLoginPageIsShown();
    }

    @Then("der Nutzer erhält die Nachricht, dass er registriert ist")
    public void theUserReceivesTheRegisteredMessage() {
        super.theUserReceivesTheRegisteredMessage();
    }

    @Then("der Nutzer erhält die Nachricht, dass die Logindaten ungültig sind")
    public void theUserReceivesTheMessageThatTheLoginDataIsInvalid() {
        super.theUserReceivesTheMessageThatTheLoginDataIsInvalid();
    }

}
