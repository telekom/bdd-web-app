package de.telekom.steps;

import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;

@Steps
public class TestSteps {

    @Given("test")
    public void test(){
        System.out.println("test!");
    }

}
