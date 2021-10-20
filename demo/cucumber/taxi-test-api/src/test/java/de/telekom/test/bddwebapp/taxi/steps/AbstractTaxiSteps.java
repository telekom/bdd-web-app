package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.api.steps.ApiSteps;
import de.telekom.test.bddwebapp.cucumber.steps.DataTableInteractionParameterConverter;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractTaxiSteps extends ApiSteps {

    @Autowired
    protected StoryInteraction storyInteraction;

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    protected String taxiAppUrl;

    @Value("${testdata-sim.url:http://localhost:5001/testdata-sim}")
    protected String testDataSimUrl;

    @Autowired
    protected DataTableInteractionParameterConverter dataTableInteractionParameterConverter;

    protected RequestSpecification testDataSimRequest() {
        createRequest().baseUri(testDataSimUrl);
        return jsonConfig();
    }

}
