package de.telekom.test.bddwebapp.taxi.seleniumgrid.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.fail;

@Steps
public class FailingSteps {

    @When("this step fail")
    public void thisStepFail() {
        fail("this step should be ignored");
    }

}
