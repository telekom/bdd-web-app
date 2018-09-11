package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.pages.RegistrationPage;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Steps
public class RegistrationSteps extends SeleniumSteps {

    private final Random random = new Random();
    private final Logger logger = Logger.getGlobal();

    @Value("${hostIncludingPort}")
    private String hostIncludingPort;

    @Autowired
    private StoryInteraction storyInteraction;

    @Given("the openend registration page")
    public void theOpenRegistrationPage() {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsShown();
    }

    @Given("registered user")
    public void registeredUser() {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsShown();
        validRegistrationDataForUser();
        theUserSuccessfullyCompletedTheRegistration();
    }

    private void theUserOpenTheRegistrationPage() {
        open(getUrlWithHost(hostIncludingPort, RegistrationPage.URL));
    }

    @Given("valid registration data for user")
    public void validRegistrationDataForUser() {
        scenarioInteraction.remember("firstName", "Hans");
        scenarioInteraction.remember("lastName", "Müller");
        String username = randomNumber() + "@user.de";
        scenarioInteraction.remember("username", username);
        logger.info("Generate user: " + username);
        scenarioInteraction.remember("password", "passwort");
    }

    private String randomNumber() {
        return StringUtils.leftPad("" + random.nextInt(Integer.MAX_VALUE), 12, "0");
    }

    @Given("valid registration data with error for user")
    public void validRegistrationDataWithErrorForUser() {
        scenarioInteraction.remember("firstName", "Hans");
        scenarioInteraction.remember("lastName", "Müller");
        scenarioInteraction.remember("username", "fehler@test.de");
        scenarioInteraction.remember("password", "passwort");
    }

    @Given("invalid registration data for user")
    public void invalidRegistrationDataForUser() {
        scenarioInteraction.remember("firstName", "Hans");
        scenarioInteraction.remember("lastName", "Müller");
        scenarioInteraction.remember("username", "user");
        scenarioInteraction.remember("password", "passwort");
    }

    @When("the user successfully completed the registration")
    public void theUserSuccessfullyCompletedTheRegistration() {
        RegistrationPage registrationPage = getCurrentPage();
        registrationPage.setFirstName(scenarioInteraction.recall("firstName"));
        registrationPage.setLastName(scenarioInteraction.recall("lastName"));
        registrationPage.setUsername(scenarioInteraction.recall("username"));
        registrationPage.setPassword(scenarioInteraction.recall("password"));
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
