package de.telekom.test.bddwebapp.api;

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Abstract steps class for api tests.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public abstract class ApiSteps {

    @Autowired
    protected ScenarioInteraction scenarioInteraction;

    public RequestSpecification createRequest() {
        RequestSpecification request = RestAssured.given().log().all().expect().log().all().request();
        rememberRequest(request);
        return request;
    }

    public RequestSpecification recallRequest() {
        return scenarioInteraction.recallNotNull("request");
    }

    public void rememberRequest(RequestSpecification requestSpecification) {
        scenarioInteraction.remember("request", requestSpecification);
    }

    public Response recallResponse() {
        return scenarioInteraction.recallNotNull("response");
    }

    public Map<String, Object> recallResponseAsMap() {
        try {
            return recallResponse().getBody().as(Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error converting rest recallResponse to map. Response: [" + recallResponse().getBody().asString() + "]", e);
        }
    }

}
