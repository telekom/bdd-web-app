package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.cucumber.steps.DataTableInteractionParameterConverter;
import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractTaxiSteps extends SeleniumSteps {

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    protected String taxiAppUrl;

    @Value("${reservation-api-sim.url:http://localhost:5001/reservation-api-sim}")
    protected String testDataSimUrl;

    @Autowired
    protected DataTableInteractionParameterConverter dataTableInteractionParameterConverter;

    protected RequestSpecification testDataSimRequest() {
        createRequest().baseUri(testDataSimUrl);
        return jsonConfig();
    }

}
