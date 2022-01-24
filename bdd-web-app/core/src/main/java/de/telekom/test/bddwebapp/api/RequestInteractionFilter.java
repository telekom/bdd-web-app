package de.telekom.test.bddwebapp.api;

import de.telekom.test.bddwebapp.api.steps.ApiSteps;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.AllArgsConstructor;

/**
 * Filter for Rest Assured that saves the current recallResponse in the Scenario Interaction.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@AllArgsConstructor
public class RequestInteractionFilter implements Filter {

    private final ScenarioInteraction scenarioInteraction;

    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        scenarioInteraction.remember(ApiSteps.RESPONSE_INTERACTION_KEY, response);
        return response;
    }

}
