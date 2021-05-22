package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.jbehave.steps.Steps;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.fail;

@Steps
public class FailingSteps {

    @When("this step fail")
    public void thisStepFail() {
        fail("this step should be ignored");
    }

}
