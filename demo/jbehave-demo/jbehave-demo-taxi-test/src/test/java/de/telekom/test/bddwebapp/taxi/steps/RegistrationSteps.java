package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.jbehave.steps.Steps;
import de.telekom.test.bddwebapp.jbehave.steps.StoryInteractionParameterConverter;
import de.telekom.test.bddwebapp.taxi.pages.RegistrationPage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static de.telekom.test.bddwebapp.frontend.util.UrlAppender.appendUrl;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class RegistrationSteps extends AbstractTaxiSteps {

    @Autowired
    private StoryInteractionParameterConverter storyInteractionParameterConverter;

    @Given("the opened registration page")
    public void theOpenedRegistrationPage() {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsShown();
    }

    @Given("registered user as $testobject")
    public void registeredUser(String testobject) {
        theOpenedRegistrationPage();
        Map<String, Object> registration = Map.of(
                "firstName", "Max",
                "lastName", "Mustermann",
                "username", "max@mustermann" + randomNumeric(8) + ".de",
                "password", "password1234"
        );
        theUserRegister(registration);
        storyInteraction.rememberObject(testobject, registration);
    }

    @When("the user open the registration page")
    private void theUserOpenTheRegistrationPage() {
        open(appendUrl(taxiAppUrl, RegistrationPage.URL));
    }

    @When("the user register with $testData")
    public void theUserRegister(ExamplesTable testData) {
        storyInteractionParameterConverter.getRowsWithInteractionKey(testData).forEach(this::theUserRegister);
    }

    private void theUserRegister(Map<String, ?> testData) {
        RegistrationPage registrationPage = getCurrentPage();
        registrationPage.setFirstName(testData.get("firstName").toString());
        registrationPage.setLastName(testData.get("lastName").toString());
        registrationPage.setUsername(testData.get("username").toString());
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
