package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.cucumber.steps.AbstractCucumberSpringConfigurationSteps;
import de.telekom.test.bddwebapp.taxi.config.TestConfig;
import io.cucumber.java.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

import java.time.ZonedDateTime;
import java.util.UUID;

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
        storyInteraction.remember("NOW", ZonedDateTime.now().toString());
        storyInteraction.remember("RANDOM", UUID.randomUUID().toString());
    }

    @ParameterType(".*")
    public String interactionKey(String interactionKey) {
        return resolveInteractionKey(interactionKey);
    }

}
