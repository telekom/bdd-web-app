package de.telekom.test.bddwebapp.taxi.docker.steps;

import de.telekom.test.bddwebapp.jbehave.steps.Steps;
import de.telekom.test.bddwebapp.taxi.steps.AbstractTaxiSteps;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.Matchers.is;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class ActuatorSteps extends AbstractTaxiSteps {

    @When("request readiness probe")
    public void requestReadinessProbe() {
        taxiAppJsonRequest().basePath("actuator/readiness").get();
    }

    @Then("the readiness check is successful")
    public void theReadinessCheckIsSuccessful() {
        recallResponse().then()
                .statusCode(200)
                .body("ready", is(true));
    }

}
