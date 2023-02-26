package de.telekom.test.steps;

import de.telekom.test.config.TestConfig;
import de.telekom.test.bddwebapp.cucumber.steps.AbstractCucumberSpringConfigurationSteps;
import io.cucumber.java.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@CucumberContextConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class CucumberGlueSteps extends AbstractCucumberSpringConfigurationSteps {

    @Before(order = 0)
    public void firstBeforeHook(Scenario scenario) {
        setupBddWebApp(scenario);
    }

    @ParameterType(".*")
    public String interactionKey(String interactionKey) {
        return resolveInteractionKey(interactionKey);
    }

}
