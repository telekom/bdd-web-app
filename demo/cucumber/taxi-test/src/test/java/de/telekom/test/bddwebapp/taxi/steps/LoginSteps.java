package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.taxi.pages.LoginPage;
import de.telekom.test.bddwebapp.taxi.pages.ReservationPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static de.telekom.test.bddwebapp.frontend.util.UrlAppender.appendUrl;
import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class LoginSteps extends AbstractTaxiSteps {

    @Given("the opened login page")
    public void theOpenedLoginPage() {
        theUserOpensTheLoginPage();
        theLoginPageIsShown();
    }

    @Given("logged in customer {}")
    public void loggedInCustomer(String testobject) {
        testDataSimRequest().post("/testdata/user").then().statusCode(200);
        storyInteraction.rememberObject(testobject, recallResponseAsMap());
        theUserOpensTheLoginPage();
        theLoginPageIsShown();
        theUserLogsIn(storyInteraction.recallObjectNotNull(testobject, "username"),
                storyInteraction.recallObjectNotNull(testobject, "password"));
        createExpectedPage(ReservationPage.class);
    }

    @When("the user opens the login page")
    public void theUserOpensTheLoginPage() {
        open(appendUrl(taxiAppUrl, LoginPage.URL));
    }

    @When("the user logs in with {interactionKey} {interactionKey}")
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
