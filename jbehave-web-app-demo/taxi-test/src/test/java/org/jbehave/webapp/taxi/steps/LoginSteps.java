package org.jbehave.webapp.taxi.steps;

import org.jbehave.webapp.taxi.pages.LoginPage;
import org.jbehave.webapp.frontend.steps.SeleniumSteps;
import org.jbehave.webapp.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

@Steps
public class LoginSteps extends SeleniumSteps {

    @Autowired
    private HomepageSteps homepageSteps;

    @Autowired
    private ReservationSteps reservationSteps;

    @Autowired
    private RegistrationSteps registrationSteps;

    @Given("ein eingeloggter Kunde $user")
    public void loggedInCustomer(String user) {
        registrationSteps.registeredUser(user);
        homepageSteps.theUserOpensTheHomePage();
        theStartPageIsOpen();
        theUserLogsIn(user);
        reservationSteps.theReservationPageOpens();
    }

    @When("der Anwender $user sich einloggt")
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
    public void theStartPageIsOpen() {
        createExpectedPage(LoginPage.class);
    }

    @Then("der Anwender erhält die Nachricht, dass er registriert ist")
    public void theUserReceivesTheRegisteredMessage() {
        LoginPage loginPage = getCurrentPage();
        assertTrue(loginPage.registeredMessageIsShown());
    }

}
