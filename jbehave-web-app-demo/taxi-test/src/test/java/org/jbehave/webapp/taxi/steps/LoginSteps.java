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
    private HomepageSteps homepageSteps;

    @Autowired
    private ReservationSteps reservationSteps;

    @Autowired
    private RegistrationSteps registrationSteps;

    @Given("die geöffnete Loginseite")
    public void theOpenLoginPage() {
        theUserOpensTheLoginPage();
        theLoginPageIsOpen();
    }

    @Given("ein eingeloggter Nutzer $user")
    public void loggedInCustomer(String user) {
        registrationSteps.registeredUser(user);
        homepageSteps.theUserOpensTheHomePage();
        theLoginPageIsOpen();
        theUserLogsIn(user);
        reservationSteps.theReservationPageOpens();
    }

    @Given("invalide Logindaten für Nutzer $user")
    public void invalidLogInDataForUser(String user) {
        storyInteraction.rememberObject(user, "username", "invalid@user.de");
        storyInteraction.rememberObject(user, "password", "passwort");
    }

    @When("der Nutzer die Startseite öffnet")
    public void theUserOpensTheLoginPage() {
        open(getUrlWithHost(hostIncludingPort, LoginPage.URL));
    }

    @When("der Nutzer $user sich einloggt")
    public void theUserLogsIn(String user) {
        LoginPage loginPage = getCurrentPage();
        loginPage.setUsername(storyInteraction.recallObject(user, "username"));
        loginPage.setPassword(storyInteraction.recallObject(user, "password"));
        loginPage.submitLogin();
    }

    @When("der Nutzer den Link zur Registrierung anklickt")
    public void userClicksTheLinkToTheRegistration() {
        LoginPage loginPage = getCurrentPage();
        loginPage.clickRegistration();
    }

    @Then("ist die Loginseite geöffnet")
    public void theLoginPageIsOpen() {
        createExpectedPage(LoginPage.class);
    }

    @Then("der Nutzer erhält die Nachricht, dass er registriert ist")
    public void theUserReceivesTheRegisteredMessage() {
        LoginPage loginPage = getCurrentPage();
        assertTrue(loginPage.registeredMessageIsShown());
    }

    @Then("der Nutzer erhält die Nachricht, dass die Logindaten ungültig sind")
    public void theUserReceivesTheMessageThatTheLogindataIsInvalid() {
        LoginPage loginPage = getCurrentPage();
        assertTrue(loginPage.logindataIsInvalidMessageIsShown());
    }

}
