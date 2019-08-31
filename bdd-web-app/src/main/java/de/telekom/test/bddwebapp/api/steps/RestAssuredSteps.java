package de.telekom.test.bddwebapp.api.steps;

import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URI;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.StringUtils.isBlank;

public interface RestAssuredSteps {

    String REQUEST_INTERACTION_KEY = "request";
    String RESPONSE_INTERACTION_KEY = "response";

    RequestSpecification recallRequest();

    void rememberRequest(RequestSpecification requestSpecification);

    /**
     * Recall the current response from story interaction. The response is set a RequestInteractionFilter.
     */
    Response recallResponse();

    default Map<String, Object> recallResponseAsMap() {
        try {
            return recallResponse().getBody().as(Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error converting rest recallResponse to map. Response: [" + recallResponse().getBody().asString() + "]", e);
        }
    }

    default RequestSpecification createRequest() {
        RequestSpecification request = given().log().all().expect().log().all().request();
        rememberRequest(request);
        return request;
    }

    default RequestSpecification createRequestWithBaseUriAndProxy(String host, String apiPath, String proxyHost, String proxyPort) {
        createRequest();
        baseUri(host, apiPath);
        proxy(proxyHost, proxyPort);
        return recallRequest();
    }

    default RequestSpecification createRequestWithJsonConfig(String host, String apiPath, String proxyHost, String proxyPort) {
        createRequestWithBaseUriAndProxy(host, apiPath, proxyHost, proxyPort);
        jsonConfig();
        return recallRequest();
    }

    default RequestSpecification baseUri(String host, String apiPath) {
        RequestSpecification requestSpecification = recallRequest();
        try {
            URI baseURI = new URI(host);
            requestSpecification.baseUri(baseURI.toString()).basePath(apiPath);
            int port = baseURI.getPort() > 0 ? baseURI.getPort() : determineStandardPortForScheme(baseURI.getScheme());
            requestSpecification.port(port);
        } catch (Exception ex) {
            throw new RuntimeException("Error setting baseUri for recallRequest", ex);
        }
        return requestSpecification;
    }

    default int determineStandardPortForScheme(String scheme) {
        if (scheme.equalsIgnoreCase("https")) {
            return 443;
        } else {
            return 80;
        }
    }

    default RequestSpecification proxy(String proxyHost, String proxyPort) {
        if (isBlank(proxyHost) || isBlank(proxyPort)) {
            return recallRequest();
        }
        return proxy(proxyHost, new Integer(proxyPort));
    }

    default RequestSpecification proxy(String proxyHost, Integer proxyPort) {
        if (isBlank(proxyHost) || proxyPort == null || proxyPort == 0) {
            return recallRequest();
        }
        return recallRequest().proxy(proxyHost, proxyPort);
    }

    default RequestSpecification jsonConfig() {
        RestAssuredConfig restAssuredConfig = new RestAssuredConfig()
                .decoderConfig(new DecoderConfig("UTF-8"))
                .encoderConfig(new EncoderConfig("UTF-8", "UTF-8"))
                .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON));

        RequestSpecification requestSpecification = recallRequest();
        requestSpecification.config(restAssuredConfig);
        requestSpecification.header("Accept", ContentType.JSON.toString());
        requestSpecification.header("Content-Type", ContentType.JSON.toString());
        return requestSpecification;
    }

}
