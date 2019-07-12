package de.telekom.test.bddwebapp.frontend.steps;

import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.stories.customizing.CurrentStory;
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jbehave.core.annotations.*;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebDriverLifecycleSteps {

    @NonNull
    protected final CurrentStory currentStory;
    @NonNull
    protected final CustomizingStories customizingStories;
    @NonNull
    protected final WebDriverWrapper webDriverWrapper;
    @NonNull
    protected final BrowserDriverUpdater browserDriverUpdater;

    @BeforeStories
    public void beforeStories() {
        if (!customizingStories.isApiOnlyForAllStories() && !customizingStories.storyClassesContainsOnlyApiOnlyStories()) {
            browserDriverUpdater.updateDriver();
        }
    }

    @BeforeStory
    public void beforeStory() {
        if (!currentStory.isRestartBrowserBeforeScenario() && !currentStory.isApiOnly()) {
            webDriverWrapper.loadWebdriver();
        }
    }

    @BeforeScenario(uponType = ScenarioType.NORMAL)
    public void beforeScenarioForNormal() {
        beforeScenario(ScenarioType.NORMAL);
    }

    @BeforeScenario(uponType = ScenarioType.EXAMPLE)
    public void beforeScenarioForExample() {
        beforeScenario(ScenarioType.EXAMPLE);
    }

    @AfterStory
    public void afterStory() {
        webDriverWrapper.quit();
    }

    @AfterStories
    public void afterStories() {
        webDriverWrapper.quit();
    }

    protected void beforeScenario(ScenarioType type) {
        if (currentStory.isRestartBrowserBeforeScenario()) {
            webDriverWrapper.quit();
            webDriverWrapper.loadWebdriver();
        }
    }

}
