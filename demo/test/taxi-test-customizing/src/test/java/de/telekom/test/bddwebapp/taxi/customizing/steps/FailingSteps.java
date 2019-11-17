package de.telekom.test.bddwebapp.taxi.customizing.steps;

import io.cucumber.java.en.When;

import static org.junit.Assert.fail;

public class FailingSteps {

    @When("this step fail")
    public void thisStepFail() {
        fail("this step should be ignored");
    }

}
