package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.jbehave.steps.Steps;
import de.telekom.test.bddwebapp.taxi.steps.AbstractTaxiSteps;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class FilterTestSteps extends AbstractTaxiSteps {

    @When("set parameter to $param")
    public void setParameterToParam(String param) {
        storyInteraction.remember("param", param);
    }

    @Then("parameter is $expectedParam")
    public void parameterIs(String param) {
        String expectedParam = storyInteraction.recallNotNull("param");
        assertThat(expectedParam, is(param));
    }

}
