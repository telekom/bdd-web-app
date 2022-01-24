package de.telekom.test.bddwebapp.api.steps;

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract steps class for api tests.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
public abstract class ApiSteps implements RestAssuredSteps {

    @Autowired
    protected ScenarioInteraction scenarioInteraction;

    public void clearRequest() {
        scenarioInteraction.clear(REQUEST_INTERACTION_KEY);
        scenarioInteraction.clear(RESPONSE_INTERACTION_KEY);
    }

    @Override
    public RequestSpecification recallRequest() {
        return scenarioInteraction.recallNotNull(REQUEST_INTERACTION_KEY);
    }

    @Override
    public void rememberRequest(RequestSpecification requestSpecification) {
        scenarioInteraction.remember(REQUEST_INTERACTION_KEY, requestSpecification);
    }

    @Override
    public Response recallResponse() {
        return scenarioInteraction.recallNotNull(RESPONSE_INTERACTION_KEY);
    }

}
