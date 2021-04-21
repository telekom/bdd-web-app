package de.telekom.test.bddwebapp.taxi.customizing.steps;

import io.cucumber.java.en.Then;

import static org.junit.Assert.fail;

public class FailingSteps {

    @Then("this step fail")
    public void thisStepFail() {
        fail("this step should be ignored");
    }

}
