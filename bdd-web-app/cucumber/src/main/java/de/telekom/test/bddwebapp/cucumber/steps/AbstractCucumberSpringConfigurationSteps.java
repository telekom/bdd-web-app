package de.telekom.test.bddwebapp.cucumber.steps;

import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
public abstract class AbstractCucumberSpringConfigurationSteps extends AbstractCucumberApiSpringConfigurationSteps {

    @Autowired
    protected WebDriverLifeCycle webDriverLifeCycle;

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    @Override
    public void setupBddWebApp(Scenario scenario) {
        beforeAllHook();
        beforeScenarioHook(scenario);
    }

    private void beforeScenarioHook(Scenario scenario) {
        currentFeature.beforeScenarioHook(scenario);
        webDriverLifeCycle.beforeScenarioHook(scenario);

        startScenarioInteraction(scenario);
        startStoryInteraction(scenario);
    }

    private void beforeAllHook() {
        if(beforeAll){
            setLogLevel();
            updateWebdriver();
            registerWebdriverShutdownHook();
        }
    }

    private void updateWebdriver() {
        webDriverLifeCycle.updateDriver();
    }

    private void registerWebdriverShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> webDriverLifeCycle.quitBrowser()));
    }

}
