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

@Steps
public class RegistrationSteps extends SeleniumSteps {

    private final Random random = new Random();

    @Autowired
    private StoryInteraction storyInteraction;

    @Given("ist ein registrierter Anwender $user")
    public void registeredUser(String user) {
        theUserOpenTheRegistrationPage();
        theRegistrationPageOpens();
        validRegistrationDataForUser(user);
        theUserSuccessfullyCompletedTheRegistration(user);
    }

    @Given("valide Registrierungsdaten für Nutzer $user")
    public void validRegistrationDataForUser(String user) {
        storyInteraction.rememberObject(user, "firstName", "Hans");
        storyInteraction.rememberObject(user, "lastName", "Müller");
        storyInteraction.rememberObject(user, "username", randomNumber() + "@user.de");
        storyInteraction.rememberObject(user, "password", "pa55w0rd");
    }

    private String randomNumber() {
        return StringUtils.leftPad("" + random.nextInt(Integer.MAX_VALUE), 12, "0");
    }

    @When("der Nutzer die Registrierungsseite öffnet")
    private void theUserOpenTheRegistrationPage() {
        open(getUrlWithHost("localhost:8080", "", RegistrationPage.URL));
    }

    @Then("öffnet sich die Registrierungsseite")
    public void theRegistrationPageOpens() {
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
