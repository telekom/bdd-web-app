package de.telekom.test.bddwebapp.taxi.customizing.steps;

import io.cucumber.java.en.Then;

import static org.junit.Assert.fail;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class FailingSteps {

    @Then("this step fail")
    public void thisStepFail() {
        fail("this step should be ignored");
    }

}
