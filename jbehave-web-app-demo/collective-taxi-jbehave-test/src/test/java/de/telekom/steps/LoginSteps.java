package de.telekom.steps;

import de.telekom.pages.LoginPage;
import de.telekom.pages.ReservationPage;
import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
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

    @Given("ein eingeloggter Kunde $customer")
    public void loggedInCustomer(String customer) {
        homepageSteps.theUserOpensTheHomePage();
        theStartPageIsOpen();
        theUserLogsIn();
        reservationSteps.theReservationPageOpens();
    }

    @When("der Anwender sich einloggt")
    public void theUserLogsIn() {
        LoginPage loginPage = getCurrentPage();
        loginPage.setUsername("customer");
        loginPage.setPassword("test1234");
        loginPage.submitLogin();
    }

    @When("der Nutzer den Link zur Registrierung anklickt")
    public void userClicksTheLinkToTheRegistration() {
        LoginPage loginPage = getCurrentPage();
        loginPage.clickRegistration();
    }

    @Then("dann ist die Loginseite geöffnet")
    public void theStartPageIsOpen() {
        createExpectedPage(LoginPage.class);
    }
    @Then("der Anwender erhält die Nachricht, dass er registriert ist")
    public void theUserReceivesTheRegisteredMessage() {
        LoginPage loginPage = getCurrentPage();
        assertTrue(loginPage.registeredMessageIsShown());
    }

}
