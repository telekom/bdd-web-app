package de.telekom.test.steps;

import de.telekom.test.config.TestConfig;
import de.telekom.test.bddwebapp.cucumber.steps.AbstractCucumberSpringConfigurationSteps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@CucumberContextConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class CucumberGlueSteps extends AbstractCucumberSpringConfigurationSteps {

    @Before(order = 0)
    public void initialHook(Scenario scenario) {
        setupBddWebApp(scenario);
        storyInteraction.remember("NOW", ZonedDateTime.now().toString());
        storyInteraction.remember("RANDOM", UUID.randomUUID().toString());

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                webDriverLifeCycle.quitBrowserAfterStories()));
    }

    @After
    public void afterFeature(Scenario scenario) {
        super.afterFeature(scenario);
    }

    @ParameterType(".*")
    public String interactionKey(String interactionKey) {
        return resolveInteractionKey(interactionKey);
    }

}
