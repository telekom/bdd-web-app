package de.telekom.test.bddwebapp.cucumber.hook;

import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.stories.customizing.CurrentFeature;
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.*;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class WebDriverLifeCycle {

    @Autowired
    protected CurrentFeature currentStory;
    @Autowired
    protected CustomizingStories customizingStories;
    @Autowired
    protected WebDriverWrapper webDriverWrapper;
    @Autowired
    protected BrowserDriverUpdater browserDriverUpdater;

    @Before(order = BEFORE_ALL_ORDER)
    public void updateDriver() {
        if (isBeforeAll()) {
            if (!customizingStories.isApiOnlyForAllStories()) {
                browserDriverUpdater.updateDriver();
            }
        }
    }

    @Before(order = BEFORE_FEATURE_ORDER)
    public void loadWebdriver() {
        if (isBeforeFeature()) {
            if (!currentStory.isRestartBrowserBeforeScenario() && !currentStory.isApiOnly()) {
                webDriverWrapper.quit();
                webDriverWrapper.loadWebdriver();
            }
        }
    }

    @Before
    public void beforeScenario() {
        if (currentStory.isRestartBrowserBeforeScenario()) {
            webDriverWrapper.quit();
            webDriverWrapper.loadWebdriver();
        }
    }

}
