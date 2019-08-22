package de.telekom.test.bddwebapp.api.steps;

import de.telekom.test.bddwebapp.api.RequestInteractionFilter;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.steps.Steps;
import io.restassured.RestAssured;
import org.jbehave.core.annotations.BeforeStories;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class RestAssuredSteps {

    @Autowired
    protected ScenarioInteraction scenarioInteraction;

    @BeforeStories
    public void beforeStories() {
        RestAssured.filters(Arrays.asList(new RequestInteractionFilter(scenarioInteraction)));
    }

}
