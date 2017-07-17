package de.telekom.steps;

import de.telekom.pages.LoginPage;
import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

@Steps
public class HomepageSteps extends SeleniumSteps {

    @When("der Nutzer die Startseite öffnet")
    public void theUserOpensTheHomePage() {
        open(getUrlWithHost("localhost:8080", "", LoginPage.URL));
    }

}
