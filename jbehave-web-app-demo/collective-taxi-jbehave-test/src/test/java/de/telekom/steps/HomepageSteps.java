package de.telekom.steps;

import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

@Steps
public class HomepageSteps extends SeleniumSteps {

    @Given("eine geöffnete Startseite")
    public void homePageIsOpen(){

    }

    @When("der Nutzer die Startseite öffnet")
    public void theUserOpensTheHomePage() {

    }

    @When("der Nutzer den Link zur Registrierung anklickt")
    public void userClicksTheLinkToTheRegistration() {

    }

    @Then("dann ist die Startseite geöffnet")
    public void theStartPageIsOpen() {

    }

    @Then("die Startseite enthält einen Link zur Registrierung")
    public void startPageContainsLinkToTheRegistration() {

    }

}
