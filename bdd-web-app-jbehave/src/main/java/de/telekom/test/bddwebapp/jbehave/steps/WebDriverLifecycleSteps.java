package de.telekom.test.bddwebapp.jbehave.steps;

import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.jbehave.stories.customizing.CurrentStory;
import de.telekom.test.bddwebapp.jbehave.stories.customizing.CustomizingStories;
import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class WebDriverLifecycleSteps {

    @Autowired
    protected CurrentStory currentStory;
    @Autowired
    protected CustomizingStories customizingStories;
    @Autowired
    protected WebDriverWrapper webDriverWrapper;
    @Autowired
    protected BrowserDriverUpdater browserDriverUpdater;

    @BeforeStories
    public void updateDriver() {
        browserDriverUpdater.updateDriver();
    }

    @BeforeStory
    public void setAlternativeWebDriverConfiguration() {
        var alternativeWebDriverConfiguration = currentStory.getAlternativeWebDriverConfiguration();
        if (alternativeWebDriverConfiguration.isPresent()) {
            webDriverWrapper.setAlternativeWebDriverConfiguration(alternativeWebDriverConfiguration.get());
        } else {
            webDriverWrapper.resetAlternativeWebDriverConfiguration();
        }

    }

    @AfterScenario
    public void quitBrowserAfterScenario() {
        if (currentStory.isRestartBrowserBeforeScenario()) {
            webDriverWrapper.quit();
        }
    }

    @AfterStory
    public void quitBrowserAfterStory() {
        webDriverWrapper.quit();
    }

    @AfterStories
    public void quitBrowserAfterStories() {
        webDriverWrapper.quit();
    }

}
