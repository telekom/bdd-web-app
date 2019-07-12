package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.pages.RegistrationPage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class RegistrationSteps extends AbstractTaxiSteps {

    @Given("the openend registration page")
    public void theOpenRegistrationPage() {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsShown();
    }

    @Given("registered user as $testobject")
    public void registeredUser(String testobject) {
        testDataSimRequest().post("/testdata/user").then().statusCode(200);
        storyInteraction.rememberObject(testobject, recallResponseAsMap());
    }

    @When("the user open the registration page")
    private void theUserOpenTheRegistrationPage() {
        open(appendUrl(taxiAppUrl, RegistrationPage.URL));
    }

    @When("the user register with $testData")
    public void theUserRegister(ExamplesTable testData) {
        storyInteractionParameterConverter.getRowsWithInteractionKey(testData).forEach(this::theUserRegister);
    }

    private void theUserRegister(Map<String, String> testData) {
        RegistrationPage registrationPage = getCurrentPage();
        registrationPage.setFirstName(testData.get("firstName"));
        registrationPage.setLastName(testData.get("lastName"));
        registrationPage.setUsername(testData.get("userName"));
        registrationPage.setPassword(testData.get("password"));
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
