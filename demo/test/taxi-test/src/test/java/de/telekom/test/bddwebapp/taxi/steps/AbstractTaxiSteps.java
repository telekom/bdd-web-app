package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractTaxiSteps extends SeleniumSteps {

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    protected String taxiAppUrl;

    @Value("${testdata-sim.url:http://localhost:6000/testdata-sim}")
    protected String testDataSimUrl;

    protected RequestSpecification testDataSimRequest() {
        createRequest().baseUri(testDataSimUrl);
        return jsonConfig();
    }

}
