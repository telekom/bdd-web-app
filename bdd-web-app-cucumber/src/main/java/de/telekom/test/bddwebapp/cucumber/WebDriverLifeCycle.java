package de.telekom.test.bddwebapp.cucumber;

import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.stories.customizing.CurrentFeature;
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class WebDriverLifeCycle {

    @Autowired
    protected CurrentFeature currentStory;
    @Autowired
    protected CustomizingStories customizingStories;
    @Autowired
    protected WebDriverWrapper webDriverWrapper;
    @Autowired
    protected BrowserDriverUpdater browserDriverUpdater;

    public void updateDriver() {
        browserDriverUpdater.updateDriver();
    }

    public void quitBrowserAfterStory() {
        webDriverWrapper.quit();
    }

    public void quitBrowserAfterScenario() {
        if (currentStory.isRestartBrowserBeforeScenario()) {
            webDriverWrapper.quit();
        }
    }

    public void quitBrowserAfterStories() {
        webDriverWrapper.quit();
    }

}
