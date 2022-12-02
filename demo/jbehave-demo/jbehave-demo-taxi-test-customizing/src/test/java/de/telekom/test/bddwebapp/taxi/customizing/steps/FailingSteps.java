package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.jbehave.steps.Steps;
import org.jbehave.core.annotations.When;

import static org.junit.Assert.fail;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class FailingSteps {

    @When("this step fail")
    public void thisStepFail() {
        fail("this step should be ignored");
    }

}
