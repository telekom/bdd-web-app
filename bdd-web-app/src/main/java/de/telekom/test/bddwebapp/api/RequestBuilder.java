package de.telekom.test.bddwebapp.api;

import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.http.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;
import java.util.Collection;
import java.util.List;
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

    public void setResponse(Response response) {
        this.response = response;
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

    public RequestBuilder put(String path, String... var) {
        response = requestSpecification.put(path, var);
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

    public RequestBuilder post(String path, String... var) {
        response = requestSpecification.post(path, var);
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

    public RequestBuilder get(String path, String... var) {
        response = requestSpecification.get(path, var);
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

    public RequestBuilder delete(String path, String... var) {
        response = requestSpecification.delete(path, var);
        return this;
    }

    public RequestBuilder body(byte[] var1) {
        requestSpecification.body(var1);
        return this;
    }

    public RequestBuilder body(File var1) {
        requestSpecification.body(var1);
        return this;
    }

    public RequestBuilder body(InputStream var1) {
        requestSpecification.body(var1);
        return this;
    }

    public RequestBuilder body(Object var1) {
        requestSpecification.body(var1);
        return this;
    }

    public RequestBuilder body(Object var1, ObjectMapper var2) {
        requestSpecification.body(var2);
        return this;
    }

    public RequestBuilder body(Object var1, ObjectMapperType var2) {
        requestSpecification.body(var1, var2);
        return this;
    }

    public RequestBuilder cookies(String var1, Object var2, Object... var3) {
        requestSpecification.cookies(var1, var2, var3);
        return this;
    }

    public RequestBuilder cookies(Map<String, ?> var1) {
        requestSpecification.cookies(var1);
        return this;
    }

    public RequestBuilder cookies(Cookies var1) {
        requestSpecification.cookies(var1);
        return this;
    }

    public RequestBuilder cookie(String var1, Object var2, Object... var3) {
        requestSpecification.cookie(var1, var2, var3);
        return this;
    }

    public RequestBuilder cookie(String var1) {
        requestSpecification.cookie(var1);
        return this;
    }

    public RequestBuilder cookie(Cookie var1) {
        requestSpecification.cookie(var1);
        return this;
    }

    public RequestBuilder params(String var1, Object var2, Object... var3) {
        requestSpecification.params(var1, var2, var3);
        return this;
    }

    public RequestBuilder params(Map<String, ?> var1) {
        requestSpecification.params(var1);
        return this;
    }

    public RequestBuilder param(String var1, Object... var2) {
        requestSpecification.param(var1, var2);
        return this;
    }

    public RequestBuilder param(String var1, Collection<?> var2) {
        requestSpecification.param(var1, var2);
        return this;
    }

    public RequestBuilder queryParams(String var1, Object var2, Object... var3) {
        requestSpecification.queryParams(var1, var2, var3);
        return this;
    }

    public RequestBuilder queryParams(Map<String, ?> var1) {
        requestSpecification.queryParams(var1);
        return this;
    }

    public RequestBuilder queryParam(String var1, Object... var2) {
        requestSpecification.queryParam(var1, var2);
        return this;
    }

    public RequestBuilder queryParam(String var1, Collection<?> var2) {
        requestSpecification.queryParam(var1, var2);
        return this;
    }

    public RequestBuilder formParams(String var1, Object var2, Object... var3) {
        requestSpecification.formParams(var1, var2, var3);
        return this;
    }

    public RequestBuilder formParams(Map<String, ?> var1) {
        requestSpecification.formParams(var1);
        return this;
    }

    public RequestBuilder formParam(String var1, Object... var2) {
        requestSpecification.formParam(var1, var2);
        return this;
    }

    public RequestBuilder formParam(String var1, Collection<?> var2) {
        requestSpecification.formParam(var1, var2);
        return this;
    }

    public RequestBuilder pathParam(String var1, Object var2) {
        requestSpecification.pathParam(var1, var2);
        return this;
    }

    public RequestBuilder keyStore(String var1, String var2) {
        requestSpecification.keyStore(var1, var2);
        return this;
    }

    public RequestBuilder keyStore(File var1, String var2) {
        requestSpecification.keyStore(var1, var2);
        return this;
    }

    public RequestBuilder trustStore(String var1, String var2) {
        requestSpecification.trustStore(var1, var2);
        return this;
    }

    public RequestBuilder trustStore(File var1, String var2) {
        requestSpecification.trustStore(var1, var2);
        return this;
    }

    public RequestBuilder trustStore(KeyStore var1) {
        requestSpecification.trustStore(var1);
        return this;
    }

    public RequestBuilder keyStore(KeyStore var1) {
        requestSpecification.trustStore(var1);
        return this;
    }

    public RequestBuilder relaxedHTTPSValidation() {
        requestSpecification.relaxedHTTPSValidation();
        return this;
    }

    public RequestBuilder relaxedHTTPSValidation(String var1) {
        requestSpecification.relaxedHTTPSValidation(var1);
        return this;
    }

    public RequestBuilder headers(String var1, Object var2, Object... var3) {
        requestSpecification.headers(var1, var2, var3);
        return this;
    }

    public RequestBuilder headers(Map<String, ?> var1) {
        requestSpecification.headers(var1);
        return this;
    }

    public RequestBuilder headers(Headers var1) {
        requestSpecification.headers(var1);
        return this;
    }

    public RequestBuilder header(String var1, Object var2, Object... var3) {
        requestSpecification.header(var1, var2, var3);
        return this;
    }

    public RequestBuilder header(Header var1) {
        requestSpecification.header(var1);
        return this;
    }

    public RequestBuilder contentType(ContentType var1) {
        requestSpecification.contentType(var1);
        return this;
    }

    public RequestBuilder contentType(String var1) {
        requestSpecification.contentType(var1);
        return this;
    }

    public RequestBuilder accept(ContentType var1) {
        requestSpecification.accept(var1);
        return this;
    }

    public RequestBuilder accept(String var1) {
        requestSpecification.accept(var1);
        return this;
    }

    public RequestBuilder multiPart(MultiPartSpecification var1) {
        requestSpecification.multiPart(var1);
        return this;
    }

    public RequestBuilder multiPart(File var1) {
        requestSpecification.multiPart(var1);
        return this;
    }

    public RequestBuilder multiPart(String var1, File var2) {
        requestSpecification.multiPart(var1, var2);
        return this;
    }

    public RequestBuilder multiPart(String var1, File var2, String var3) {
        requestSpecification.multiPart(var1, var2, var3);
        return this;
    }

    public RequestBuilder multiPart(String var1, Object var2) {
        requestSpecification.multiPart(var1, var2);
        return this;
    }

    public RequestBuilder multiPart(String var1, Object var2, String var3) {
        requestSpecification.multiPart(var1, var2, var3);
        return this;
    }

    public RequestBuilder multiPart(String var1, String var2, Object var3, String var4) {
        requestSpecification.multiPart(var1, var2, var3, var4);
        return this;
    }

    public RequestBuilder multiPart(String var1, String var2, byte[] var3) {
        requestSpecification.multiPart(var1, var2, var3);
        return this;
    }

    public RequestBuilder multiPart(String var1, String var2, byte[] var3, String var4) {
        requestSpecification.multiPart(var1, var2, var3, var4);
        return this;
    }

    public RequestBuilder multiPart(String var1, String var2, InputStream var3) {
        requestSpecification.multiPart(var1, var2, var3);
        return this;
    }

    public RequestBuilder multiPart(String var1, String var2, InputStream var3, String var4) {
        requestSpecification.multiPart(var1, var2, var3, var4);
        return this;
    }

    public RequestBuilder multiPart(String var1, String var2) {
        requestSpecification.multiPart(var1, var2);
        return this;
    }

    public RequestBuilder multiPart(String var1, String var2, String var3) {
        requestSpecification.multiPart(var1, var2, var3);
        return this;
    }

    public RequestBuilder port(int var1) {
        requestSpecification.port(var1);
        return this;
    }

    public RequestBuilder sessionId(String var1) {
        requestSpecification.sessionId(var1);
        return this;
    }

    public RequestBuilder sessionId(String var1, String var2) {
        requestSpecification.sessionId(var1, var2);
        return this;
    }

    public RequestBuilder urlEncodingEnabled(boolean var1) {
        requestSpecification.urlEncodingEnabled(var1);
        return this;
    }

    public RequestBuilder filter(Filter var1) {
        requestSpecification.filter(var1);
        return this;
    }

    public RequestBuilder filters(List<Filter> var1) {
        requestSpecification.filters(var1);
        return this;
    }

    public RequestBuilder filters(Filter var1, Filter... var2) {
        requestSpecification.filters(var1, var2);
        return this;
    }

    public RequestBuilder noFilters() {
        requestSpecification.noFilters();
        return this;
    }

    public RequestBuilder baseUri(String var1) {
        requestSpecification.baseUri(var1);
        return this;
    }


    public RequestBuilder proxy(String var1, int var2) {
        requestSpecification.proxy(var1, var2);
        return this;
    }

    public RequestBuilder proxy(String var1) {
        requestSpecification.proxy(var1);
        return this;
    }

    public RequestBuilder proxy(int var1) {
        requestSpecification.proxy(var1);
        return this;
    }

    public RequestBuilder proxy(String var1, int var2, String var3) {
        requestSpecification.proxy(var1, var2, var3);
        return this;
    }

    public RequestBuilder proxy(URI var1) {
        requestSpecification.proxy(var1);
        return this;
    }

    public RequestBuilder proxy(ProxySpecification var1) {
        requestSpecification.proxy(var1);
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
