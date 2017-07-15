package de.telekom.steps;

import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;

@Steps
public class SessionSteps extends SeleniumSteps {

    @Given("ein ausgeloggter Kunde $customer")
    public void loggedOutCustomer(String customer) {
        webDriverWrapper.getDriver().manage().deleteAllCookies();
    }

}
