package de.telekom.test.bddwebapp.taxi.testlevel.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.annotations.Given;
import org.junit.Before;

import static org.junit.Assert.fail;

@Steps
public class FailingStep {

    @Given("failing step")
    public void failingStep(){
        fail("failing step");
    }

}
