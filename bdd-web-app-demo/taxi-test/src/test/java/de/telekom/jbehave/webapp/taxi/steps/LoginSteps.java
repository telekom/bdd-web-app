package de.telekom.jbehave.webapp.taxi.steps;

import de.telekom.jbehave.webapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.jbehave.webapp.frontend.steps.SeleniumSteps;
import de.telekom.jbehave.webapp.steps.Steps;
import de.telekom.jbehave.webapp.taxi.pages.LoginPage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Steps
public class LoginSteps extends SeleniumSteps {

    @Value("${hostIncludingPort}")
    private String hostIncludingPort;

    @Autowired
    protected WebDriverWrapper webDriverWrapper;

    @Autowired
    private ReservationSteps reservationSteps;

    @Autowired
    private RegistrationSteps registrationSteps;

    @Given("the opened login page")
    public void theOpenedLoginPage() {
        theUserOpensTheLoginPage();
        theLoginPageIsShown();
    }

    @Given("logged in customer")
    public void loggedInCustomer() {
        registrationSteps.registeredUser();
        theUserOpensTheLoginPage();
        theLoginPageIsShown();
        theUserLogsIn();
        reservationSteps.theReservationPageIsShown();
    }

    @Given("invalid log in data for user")
    public void invalidLogInDataForUser() {
        scenarioInteraction.remember("username", "invalid@user.de");
        scenarioInteraction.remember("password", "passwort");
    }

    @When("the user opens the login page")
    public void theUserOpensTheLoginPage() {
        open(getUrlWithHost(hostIncludingPort, LoginPage.URL));
    }

    @When("the user logs in")
    public void theUserLogsIn() {
        LoginPage loginPage = getCurrentPage();
        loginPage.setUsername(scenarioInteraction.recall("username"));
        loginPage.setPassword(scenarioInteraction.recall("password"));
        loginPage.submitLogin();
    }

    @When("user clicks the link to the registration")
    public void userClicksTheLinkToTheRegistration() {
        LoginPage loginPage = getCurrentPage();
        loginPage.clickRegistration();
    }

    @Then("the login page is shown")
    public void theLoginPageIsShown() {
        createExpectedPage(LoginPage.class);
    }

    @Then("the user receives the registered message")
    public void theUserReceivesTheRegisteredMessage() {
        LoginPage loginPage = getCurrentPage();
        assertTrue(loginPage.registeredMessageIsShown());
    }

    @Then("the user receives the message that the login data is invalid")
    public void theUserReceivesTheMessageThatTheLoginDataIsInvalid() {
        LoginPage loginPage = getCurrentPage();
        assertTrue(loginPage.loginDataIsInvalidMessageIsShown());
    }

}
