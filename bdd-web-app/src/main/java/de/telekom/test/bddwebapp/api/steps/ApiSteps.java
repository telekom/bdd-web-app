package de.telekom.test.bddwebapp.api.steps;

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
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
@Slf4j
public abstract class ApiSteps {

    public static String REQUEST_INTERACTION_KEY = "request";
    public static String RESPONSE_INTERACTION_KEY = "response";

    @Autowired
    protected ScenarioInteraction scenarioInteraction;

    public void clearRequest() {
        scenarioInteraction.remember(REQUEST_INTERACTION_KEY, null);
        scenarioInteraction.remember(RESPONSE_INTERACTION_KEY, null);
    }

    public RequestSpecification recallRequest() {
        return scenarioInteraction.recallNotNull(REQUEST_INTERACTION_KEY);
    }

    public void rememberRequest(RequestSpecification requestSpecification) {
        scenarioInteraction.remember(REQUEST_INTERACTION_KEY, requestSpecification);
    }

    /**
     * Recall the current response from story interaction. The response is set a RequestInteractionFilter.
     */
    public Response recallResponse() {
        return scenarioInteraction.recallNotNull(RESPONSE_INTERACTION_KEY);
    }

    public Map<String, Object> recallResponseAsMap() {
        try {
            return recallResponse().getBody().as(Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error converting rest recallResponse to map. Response: [" + recallResponse().getBody().asString() + "]", e);
        }
    }

    public RequestSpecification createRequest() {
        RequestSpecification request = RestAssured.given().log().all().expect().log().all().request();
        rememberRequest(request);
        return request;
    }

    public RequestSpecification createRequestWithBaseUriAndProxy(String host, String apiPath, String proxyHost, String proxyPort) {
        createRequest();
        baseUri(host, apiPath);
        proxy(proxyHost, proxyPort);
        return recallRequest();
    }

    public RequestSpecification createRequestWithJsonConfig(String host, String apiPath, String proxyHost, String proxyPort) {
        createRequestWithBaseUriAndProxy(host, apiPath, proxyHost, proxyPort);
        jsonConfig();
        return recallRequest();
    }

    public RequestSpecification baseUri(String host, String apiPath) {
        RequestSpecification requestSpecification = recallRequest();
        try {
            final URI baseURI = new URI(host);
            requestSpecification.baseUri(baseURI.toString()).basePath(apiPath);
            int port = baseURI.getPort() > 0 ? baseURI.getPort() : determineStandardPortForScheme(baseURI.getScheme());
            requestSpecification.port(port);
        } catch (Exception ex) {
            throw new RuntimeException("Error setting baseUri for recallRequest", ex);
        }
        return requestSpecification;
    }

    private int determineStandardPortForScheme(String scheme) {
        if (scheme.equalsIgnoreCase("https")) {
            return 443;
        } else {
            return 80;
        }
    }

    public RequestSpecification proxy(String proxyHost, String proxyPort) {
        try {
            if (StringUtils.isNotBlank(proxyHost) && StringUtils.isNotBlank(proxyPort)) {
                recallRequest().proxy(proxyHost, new Integer(proxyPort));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error setting proxy for recallRequest", ex);
        }
        return recallRequest();
    }

    public RequestSpecification jsonConfig() {
        RestAssuredConfig restAssuredConfig = new RestAssuredConfig().decoderConfig(new DecoderConfig("UTF-8"))
                .encoderConfig(new EncoderConfig("UTF-8", "UTF-8"))
                .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON));

        RequestSpecification requestSpecification = recallRequest();
        requestSpecification.config(restAssuredConfig);
        requestSpecification.header("Accept", ContentType.JSON.toString());
        requestSpecification.header("Content-Type", ContentType.JSON.toString());
        return requestSpecification;
    }

}
