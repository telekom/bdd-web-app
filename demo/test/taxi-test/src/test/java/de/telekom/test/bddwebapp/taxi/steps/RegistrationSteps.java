package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.taxi.pages.RegistrationPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static de.telekom.test.bddwebapp.util.UrlAppender.appendUrl;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class RegistrationSteps extends AbstractTaxiSteps {

    @Given("the openend registration page")
    public void theOpenRegistrationPage() {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsShown();
    }

    @Given("registered user as {}")
    public void registeredUser(String testobject) {
        testDataSimRequest().post("/testdata/user").then().statusCode(200);
        storyInteraction.rememberObject(testobject, recallResponseAsMap());
    }

    @When("the user open the registration page")
    private void theUserOpenTheRegistrationPage() {
        open(appendUrl(taxiAppUrl, RegistrationPage.URL));
    }

    @When("the user register with")
    public void theUserRegister(DataTable testData) {
        scenarioInteraction.remember("RANDOM", randomNumeric(8));
        interactionParameterConverter.getRowsWithInteractionKey(testData).forEach(this::theUserRegister);
    }

    private void theUserRegister(Map<String, Object> testData) {
        RegistrationPage registrationPage = getCurrentPage();
        registrationPage.setFirstName(testData.get("firstName").toString());
        registrationPage.setLastName(testData.get("lastName").toString());
        registrationPage.setUsername(testData.get("userName").toString());
        registrationPage.setPassword(testData.get("password").toString());
        registrationPage.submitRegistration();
    }

    @Then("the registration page is shown")
    public void theRegistrationPageIsShown() {
        createExpectedPage(RegistrationPage.class);
    }

    @Then("the user receives the message that the registration data is invalid")
    public void theUserReceivesTheMessageThatTheRegistrationDataIsInvalid() {
        RegistrationPage registrationPage = getCurrentPage();
        assertTrue(registrationPage.registrationDataIsInvalidMessageIsShown());
    }

}
