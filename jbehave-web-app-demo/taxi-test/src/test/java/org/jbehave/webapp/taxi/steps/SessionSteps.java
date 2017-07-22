package org.jbehave.webapp.taxi.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.webapp.frontend.steps.SeleniumSteps;
import org.jbehave.webapp.steps.Steps;

@Steps
public class SessionSteps extends SeleniumSteps {

    @Given("ein ausgeloggter Nutzer")
    public void loggedOutUser() {
        webDriverWrapper.getDriver().manage().deleteAllCookies();
    }

}
