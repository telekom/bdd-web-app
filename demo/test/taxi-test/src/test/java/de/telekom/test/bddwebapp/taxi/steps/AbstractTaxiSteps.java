package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.jbehave.core.annotations.BeforeScenario;
import org.springframework.beans.factory.annotation.Value;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public abstract class AbstractTaxiSteps extends SeleniumSteps {

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    protected String taxiAppUrl;

    @Value("${testdata-sim.url:http://localhost:6000/testdata-sim}")
    protected String testDataSimUrl;

    @BeforeScenario
    public void randomNumber() {
        storyInteraction.remember("RANDOM", randomNumeric(8));
    }

    protected RequestSpecification testDataSimRequest() {
        RequestSpecification request = newRequest();
        request.baseUri(testDataSimUrl);
        request.header("Accept", ContentType.JSON.toString());
        request.header("Content-Type", ContentType.JSON.toString());
        return request;
    }

}
