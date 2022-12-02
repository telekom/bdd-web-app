package de.telekom.test.bddwebapp.taxi.ger.steps;

import de.telekom.test.bddwebapp.jbehave.steps.Steps;
import de.telekom.test.bddwebapp.taxi.steps.LoginSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
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

    @When("der Nutzer sich mit $username $password einloggt")
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
