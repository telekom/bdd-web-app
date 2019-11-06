package de.telekom.test.bddwebapp.frontend.steps;

import de.telekom.test.bddwebapp.cucumber.AfterFeature;
import de.telekom.test.bddwebapp.cucumber.BeforeAll;
import de.telekom.test.bddwebapp.cucumber.BeforeFeature;
import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.stories.customizing.CurrentStory;
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
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

    @BeforeAll
    public void beforeStories() {
        if (!customizingStories.isApiOnlyForAllStories()) {
            browserDriverUpdater.updateDriver();
        }
    }

    @BeforeFeature
    public void beforeStory() {
        if (!currentStory.isRestartBrowserBeforeScenario() && !currentStory.isApiOnly()) {
            webDriverWrapper.loadWebdriver();
        }
    }

    @Before
    public void beforeScenario() {
        if (currentStory.isRestartBrowserBeforeScenario()) {
            webDriverWrapper.quit();
            webDriverWrapper.loadWebdriver();
        }
    }

    @AfterFeature
    public void afterStory() {
        webDriverWrapper.quit();
    }

    @AfterFeature
    public void afterStories() {
        webDriverWrapper.quit();
    }

}
