package de.telekom.test.bddwebapp.api;

import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

/**
 * Spring component class for request specifications.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class RequestBuilder {

    private RestAssuredConfig basicRestConfig = defaultConfiguration();

    private RequestSpecification requestSpecification;

    private Response response;

    public RestAssuredConfig defaultConfiguration() {
        return new RestAssuredConfig().decoderConfig(new DecoderConfig("UTF-8"))
                .encoderConfig(new EncoderConfig("UTF-8", "UTF-8"))
                .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON));
    }

    public void clearRequest() {
        requestSpecification = null;
        response = null;
    }

    public RequestBuilder request() {
        if (requestSpecification == null) {
            requestSpecification = RestAssured.given().log().all().expect().log().all().request();
        }
        return this;
    }

    public RequestBuilder requestWithJsonConfig(String host, String apiPath, String proxyHost, String proxyPort) {
        request();
        try {
            final URI baseURI = new URI(host);
            requestSpecification.baseUri(baseURI.toString()).basePath(apiPath);
            int port = baseURI.getPort() > 0 ? baseURI.getPort() : determineStandardPortForScheme(baseURI.getScheme());
            requestSpecification.port(port);

            if (StringUtils.isNotBlank(proxyHost) && StringUtils.isNotBlank(proxyPort)) {
                requestSpecification.proxy(proxyHost, new Integer(proxyPort));
            }

            requestSpecification.config(basicRestConfig);
            requestSpecification.header("Accept", ContentType.JSON.toString());
            requestSpecification.header("Content-Type", ContentType.JSON.toString());
        } catch (Exception ex) {
            throw new RuntimeException("Error setting baseUri, path, port and proxy for request", ex);
        }
        return this;
    }

    private int determineStandardPortForScheme(String scheme) {
        switch (scheme) {
            case "https": {
                return 443;
            }
            default: {
                return 80;
            }
        }
    }

    public RequestSpecification requestSpecification() {
        return requestSpecification;
    }

    public Response response() {
        return response;
    }

    public RequestBuilder basePath(String apiPath) {
        requestSpecification.basePath(apiPath);
        return this;
    }

    public RequestBuilder basicAuth(String apiUsername, String apiPassword) {
        requestSpecification.auth().preemptive().basic(apiUsername, apiPassword);
        return this;
    }

    public RequestBuilder headers(String type, String value) {
        requestSpecification.headers(type, value);
        return this;
    }

    public RequestBuilder pathParam(String key, String value) {
        requestSpecification.pathParam(key, value);
        return this;
    }

    public RequestBuilder pathParams(Map<String, String> pathParams) {
        requestSpecification.pathParams(pathParams);
        return this;
    }

    public RequestBuilder queryParam(String key, Object value) {
        requestSpecification.queryParam(key, value);
        return this;
    }

    public RequestBuilder body(String body) {
        requestSpecification.body(body);
        return this;
    }

    public RequestBuilder body(Map<String, Object> body) {
        requestSpecification.body(body);
        return this;
    }

    public RequestBuilder put() {
        response = requestSpecification.put();
        return this;
    }

    public RequestBuilder put(String path) {
        response = requestSpecification.put(path);
        return this;
    }

    public RequestBuilder post() {
        response = requestSpecification.post();
        return this;
    }

    public RequestBuilder post(String path) {
        response = requestSpecification.post(path);
        return this;
    }

    public RequestBuilder get() {
        response = requestSpecification.get();
        return this;
    }

    public RequestBuilder get(String path) {
        response = requestSpecification.get(path);
        return this;
    }

    public RequestBuilder delete() {
        response = requestSpecification.delete();
        return this;
    }

    public RequestBuilder delete(String path) {
        response = requestSpecification.delete(path);
        return this;
    }

    public Map<String, Object> getResponseAsMap() {
        try {
            return response.getBody().as(Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error converting rest response to map. Response: [" + response.getBody().asString() + "]", e);
        }
    }

}
