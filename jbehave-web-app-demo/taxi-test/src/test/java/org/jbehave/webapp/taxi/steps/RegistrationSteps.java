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
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;
import java.util.logging.Logger;

@Steps
public class RegistrationSteps extends SeleniumSteps {

    private final Random random = new Random();
    private final Logger logger = Logger.getGlobal();

    @Value("${hostIncludingPort}")
    private String hostIncludingPort;

    @Autowired
    private StoryInteraction storyInteraction;

    @Given("die geöffnete Registrierungsseite")
    public void theOpenRegistrationPage() {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsOpen();
    }

    @Given("ist ein registrierter Anwender")
    public void registeredUser() {
        theUserOpenTheRegistrationPage();
        theRegistrationPageIsOpen();
        validRegistrationDataForUser();
        theUserSuccessfullyCompletedTheRegistration();
    }

    @Given("valide Registrierungsdaten für Nutzer")
    public void validRegistrationDataForUser() {
        storyInteraction.remember("firstName", "Hans");
        storyInteraction.remember("lastName", "Müller");
        String username = randomNumber() + "@user.de";
        storyInteraction.remember("username", username);
        logger.info("Generate user: " + username);
        storyInteraction.remember("password", "passwort");
    }

    private String randomNumber() {
        return StringUtils.leftPad("" + random.nextInt(Integer.MAX_VALUE), 12, "0");
    }

    @Given("valide Registrierungsdaten mit Kontrollflussfehler für Nutzer")
    public void validRegistrationDataWithErrorForUser() {
        storyInteraction.remember("firstName", "Hans");
        storyInteraction.remember("lastName", "Müller");
        storyInteraction.remember("username", "fehler@test.de");
        storyInteraction.remember("password", "passwort");
    }

    @Given("invalide Registrierungsdaten für Nutzer")
    public void invalidRegistrationDataForUser() {
        storyInteraction.remember("firstName", "Hans");
        storyInteraction.remember("lastName", "Müller");
        storyInteraction.remember("username", "user");
        storyInteraction.remember("password", "passwort");
    }

    @When("der Nutzer die Registrierungsseite öffnet")
    private void theUserOpenTheRegistrationPage() {
        open(getUrlWithHost(hostIncludingPort, RegistrationPage.URL));
    }

    @Then("ist die Registrierungsseite geöffnet")
    public void theRegistrationPageIsOpen() {
        createExpectedPage(RegistrationPage.class);
    }

    @When("der Nutzer die Registrierung durchführt")
    public void theUserSuccessfullyCompletedTheRegistration() {
        RegistrationPage registrationPage = getCurrentPage();
        registrationPage.setFirstName(storyInteraction.recall("firstName"));
        registrationPage.setLastName(storyInteraction.recall("lastName"));
        registrationPage.setUsername(storyInteraction.recall("username"));
        registrationPage.setPassword(storyInteraction.recall("password"));
        registrationPage.submitRegistration();
    }

}
