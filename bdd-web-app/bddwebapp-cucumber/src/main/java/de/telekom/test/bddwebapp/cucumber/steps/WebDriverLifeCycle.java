package de.telekom.test.bddwebapp.cucumber.steps;

import de.telekom.test.bddwebapp.cucumber.features.CurrentFeature;
import de.telekom.test.bddwebapp.cucumber.features.CustomizingFeatures;
import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class WebDriverLifeCycle {

    @Autowired
    protected CurrentFeature currentFeature;
    @Autowired
    protected CustomizingFeatures customizingStories;
    @Autowired
    protected WebDriverWrapper webDriverWrapper;
    @Autowired
    protected BrowserDriverUpdater browserDriverUpdater;

    public void updateDriver() {
        browserDriverUpdater.updateDriver();
    }

    public void quitBrowser() {
        webDriverWrapper.quit();
    }

    public void beforeScenarioHook(Scenario scenario) {
        restartBrowserBeforeScenario();
        restartBrowserBeforeFeature();
    }

    public void restartBrowserBeforeScenario() {
        if (currentFeature.isRestartBrowserBeforeScenario()) {
            webDriverWrapper.quit();
        }
    }

    public void restartBrowserBeforeFeature() {
        if (currentFeature.isBeforeFeature()) {
            webDriverWrapper.quit();
        }
    }
}
