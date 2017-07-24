package org.jbehave.webapp.taxi.steps;

import org.jbehave.webapp.taxi.pages.LoginPage;
import org.jbehave.webapp.frontend.steps.SeleniumSteps;
import org.jbehave.webapp.steps.Steps;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Value;

@Steps
public class HomepageSteps extends SeleniumSteps {

    @Value("${hostIncludingPort}")
    private String hostIncludingPort;

    @When("der Nutzer die Startseite öffnet")
    public void theUserOpensTheHomePage() {
        open(getUrlWithHost(hostIncludingPort, LoginPage.URL));
    }

}
