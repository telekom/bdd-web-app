package de.telekom.test.bddwebapp.api.steps;

import de.telekom.test.bddwebapp.api.RequestInteractionFilter;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.util.Arrays.asList;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class RestAssuredLifecycle {

    @Autowired
    protected ScenarioInteraction scenarioInteraction;

    @PostConstruct
    public void beforeStories() {
        RestAssured.filters(asList(new RequestInteractionFilter(scenarioInteraction)));
    }

}
