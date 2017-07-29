package org.jbehave.webapp.taxi.steps;

import org.jbehave.webapp.taxi.pages.LoginPage;
import org.jbehave.webapp.frontend.steps.SeleniumSteps;
import org.jbehave.webapp.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.Assert.assertTrue;

@Steps
public class LoginSteps extends SeleniumSteps {

    @Value("${hostIncludingPort}")
    private String hostIncludingPort;

    @Autowired
    private ReservationSteps reservationSteps;

    @Autowired
    private RegistrationSteps registrationSteps;

    @Given("die geöffnete Loginseite")
    public void theOpenLoginPage() {
        theUserOpensTheLoginPage();
        theLoginPageIsShown();
    }

    @Given("ein eingeloggter Nutzer")
    public void loggedInCustomer() {
        registrationSteps.registeredUser();
        theUserOpensTheLoginPage();
        theLoginPageIsShown();
        theUserLogsIn();
        reservationSteps.theReservationPageIsShown();
    }

    @Given("ungültige Logindaten")
    public void invalidLogInDataForUser() {
        scenarioInteraction.remember("username", "invalid@user.de");
        scenarioInteraction.remember("password", "passwort");
    }

    @When("der Nutzer die Startseite öffnet")
    public void theUserOpensTheLoginPage() {
        open(getUrlWithHost(hostIncludingPort, LoginPage.URL));
    }

    @When("der Nutzer sich einloggt")
    public void theUserLogsIn() {
        LoginPage loginPage = getCurrentPage();
        loginPage.setUsername(scenarioInteraction.recall("username"));
        loginPage.setPassword(scenarioInteraction.recall("password"));
        loginPage.submitLogin();
    }

    @When("der Nutzer den Link zur Registrierung anklickt")
    public void userClicksTheLinkToTheRegistration() {
        LoginPage loginPage = getCurrentPage();
        loginPage.clickRegistration();
    }

    @Then("wird die Loginseite angezeigt")
    public void theLoginPageIsShown() {
        createExpectedPage(LoginPage.class);
    }

    @Then("der Nutzer erhält die Nachricht, dass er registriert ist")
    public void theUserReceivesTheRegisteredMessage() {
        LoginPage loginPage = getCurrentPage();
        assertTrue(loginPage.registeredMessageIsShown());
    }

    @Then("der Nutzer erhält die Nachricht, dass die Logindaten ungültig sind")
    public void theUserReceivesTheMessageThatTheLoginDataIsInvalid() {
        LoginPage loginPage = getCurrentPage();
        assertTrue(loginPage.loginDataIsInvalidMessageIsShown());
    }

}
