package de.telekom.test.bddwebapp.cucumber.hook;

import de.telekom.test.bddwebapp.cucumber.extension.BeforeFeature;
import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.stories.customizing.CurrentFeature;
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories;
import io.cucumber.java.After;
import org.junit.AfterClass;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void updateDriver() {
        browserDriverUpdater.updateDriver();
    }

    @BeforeFeature
    public void quitBrowserAfterStory() {
        webDriverWrapper.quit();
    }

    @AfterClass
    public void quitBrowserAfterScenario() {
        if (currentStory.isRestartBrowserBeforeScenario()) {
            webDriverWrapper.quit();
        }
    }

    public void quitBrowserAfterStories() {
        webDriverWrapper.quit();
    }

}
