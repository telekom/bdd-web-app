package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.api.RequestBuilder;
import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.pages.RegistrationPage;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 */
@Steps
public class RegistrationSteps extends SeleniumSteps {

    private final Random random = new Random();
    private final Logger logger = Logger.getGlobal();

    @Value("${hostIncludingPort}")
    private String hostIncludingPort;

    @Autowired
    private StoryInteraction storyInteraction;

    @Autowired
    private RequestBuilder requestBuilder;

    @Given("the openend registration page")
    public void theOpenRegistrationPage() {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsShown();
    }

    @Given("registered user as $testobject")
    public void registeredUser(String testobject) {
        requestBuilder.requestWithJsonConfig(hostIncludingPort, "testData/user", "", "").post();
        storyInteraction.rememberObject(testobject, requestBuilder.getResponseAsMap());
    }

    @When("the user open the registration page")
    private void theUserOpenTheRegistrationPage() {
        open(getUrlWithHost(hostIncludingPort, RegistrationPage.URL));
    }

    @When("the user register with $testData")
    public void theUserRegister(ExamplesTable testData) {
        lifecyleSteps.getRowsWithInteractionKey(testData).forEach(this::theUserRegister);
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
