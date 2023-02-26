package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.taxi.customizing.steps.registrationSteps.RegistrationSteps;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class OverrideableRegisteredUserSteps {

    @Autowired
    private RegistrationSteps registrationSteps;

    @Given("ov registered user as {}")
    public void registeredUser(String testobject) {
        registrationSteps.registeredUser(testobject);
    }

}
