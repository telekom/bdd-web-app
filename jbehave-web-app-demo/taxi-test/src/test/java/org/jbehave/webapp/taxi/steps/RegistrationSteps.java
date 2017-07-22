package org.jbehave.webapp.taxi.steps;

import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.webapp.frontend.steps.SeleniumSteps;
import org.jbehave.webapp.interaction.StoryInteraction;
import org.jbehave.webapp.steps.Steps;
import org.jbehave.webapp.taxi.pages.RegistrationPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.logging.Logger;

@Steps
public class RegistrationSteps extends SeleniumSteps {

    private final Random random = new Random();
    private final Logger logger = Logger.getGlobal();

    @Autowired
    private StoryInteraction storyInteraction;

    @Given("die geöffnete Registrierungsseite")
    public void theOpenRegistrationPage() {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsOpen();
    }

    @Given("ist ein registrierter Anwender $user")
    public void registeredUser(String user) {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsOpen();
        validRegistrationDataForUser(user);
        theUserSuccessfullyCompletedTheRegistration(user);
    }

    @Given("valide Registrierungsdaten für Nutzer $user")
    public void validRegistrationDataForUser(String user) {
        storyInteraction.rememberObject(user, "firstName", "Hans");
        storyInteraction.rememberObject(user, "lastName", "Müller");
        String username = randomNumber() + "@user.de";
        storyInteraction.rememberObject(user, "username", username);
        logger.info("Generate user: " + username);
        storyInteraction.rememberObject(user, "password", "passwort");
    }

    private String randomNumber() {
        return StringUtils.leftPad("" + random.nextInt(Integer.MAX_VALUE), 12, "0");
    }

    @Given("valide Registrierungsdaten mit Kontrollflussfehler für Nutzer $user")
    public void validRegistrationDataWithErrorForUser(String user) {
        storyInteraction.rememberObject(user, "firstName", "Hans");
        storyInteraction.rememberObject(user, "lastName", "Müller");
        storyInteraction.rememberObject(user, "username", "fehler@test.de");
        storyInteraction.rememberObject(user, "password", "passwort");
    }

    @Given("invalide Registrierungsdaten für Nutzer $user")
    public void invalidRegistrationDataForUser(String user) {
        storyInteraction.rememberObject(user, "firstName", "Hans");
        storyInteraction.rememberObject(user, "lastName", "Müller");
        storyInteraction.rememberObject(user, "username", "user");
        storyInteraction.rememberObject(user, "password", "passwort");
    }

    @When("der Nutzer die Registrierungsseite öffnet")
    private void theUserOpenTheRegistrationPage() {
        open(getUrlWithHost("localhost:8080", "", RegistrationPage.URL));
    }

    @Then("ist die Registrierungsseite geöffnet")
    public void theRegistrationPageIsOpen() {
        createExpectedPage(RegistrationPage.class);
    }

    @When("der Nutzer $user die Registrierung durchführt")
    public void theUserSuccessfullyCompletedTheRegistration(String user) {
        RegistrationPage registrationPage = getCurrentPage();
        registrationPage.setFirstName(storyInteraction.recallObject(user, "firstName"));
        registrationPage.setLastName(storyInteraction.recallObject(user, "lastName"));
        registrationPage.setUsername(storyInteraction.recallObject(user, "username"));
        registrationPage.setPassword(storyInteraction.recallObject(user, "password"));
        registrationPage.submitRegistration();
    }

}
