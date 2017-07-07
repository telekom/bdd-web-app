package de.telekom.steps;

import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;

@Steps
public class UserSessionSteps {

    @Given("ein eingeloggter Kunde $customer")
    public void loggedInCustomer(String customer){

    }

}
