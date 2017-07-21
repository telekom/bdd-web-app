package org.jbehave.webapp.taxi.steps;

import org.jbehave.webapp.taxi.pages.LoginPage;
import org.jbehave.webapp.frontend.steps.SeleniumSteps;
import org.jbehave.webapp.steps.Steps;
import org.jbehave.core.annotations.When;

@Steps
public class HomepageSteps extends SeleniumSteps {

    @When("der Nutzer die Startseite öffnet")
    public void theUserOpensTheHomePage() {
        open(getUrlWithHost("localhost:8080", "", LoginPage.URL));
    }

}
