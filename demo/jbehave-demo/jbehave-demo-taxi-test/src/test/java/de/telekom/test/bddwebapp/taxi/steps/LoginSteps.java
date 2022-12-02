package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.jbehave.steps.Steps;
import de.telekom.test.bddwebapp.taxi.pages.LoginPage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;

import static de.telekom.test.bddwebapp.frontend.util.UrlAppender.appendUrl;
import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class LoginSteps extends AbstractTaxiSteps {

    @Autowired
    private ReservationSteps reservationSteps;

    @Autowired
    private RegistrationSteps registrationSteps;

    @Given("the opened login page")
    public void theOpenedLoginPage() {
        theUserOpensTheLoginPage();
        theLoginPageIsShown();
    }

    @Given("logged in customer $testobject")
    public void loggedInCustomer(String testobject) {
        registrationSteps.registeredUser(testobject);
        theUserOpensTheLoginPage();
        theLoginPageIsShown();
        theUserLogsIn(storyInteraction.recallObjectNotNull(testobject, "username"),
                storyInteraction.recallObjectNotNull(testobject, "password"));
        reservationSteps.theReservationPageIsShown();
    }

    @When("the user opens the login page")
    public void theUserOpensTheLoginPage() {
        open(appendUrl(taxiAppUrl, LoginPage.URL));
    }

    @When("the user logs in with $username $password")
    public void theUserLogsIn(String username, String password) {
        LoginPage loginPage = getCurrentPage();
        loginPage.setUsername(username);
        loginPage.setPassword(password);
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
