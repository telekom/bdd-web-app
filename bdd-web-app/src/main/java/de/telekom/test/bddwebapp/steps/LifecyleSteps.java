package de.telekom.test.bddwebapp.steps;

import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
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
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LifecyleSteps {

    /*
     * The current selenium page of story interaction. Is automatically deleted after a story.
     */
    public static final String CURRENT_PAGE = "CURRENT_PAGE";

    @NonNull
    protected final ScenarioInteraction scenarioInteraction;
    @NonNull
    protected final StoryInteraction storyInteraction;
    @NonNull
    protected final CurrentStory currentStory;
    @NonNull
    protected final CustomizingStories customizingStories;
    @NonNull
    protected final WebDriverWrapper webDriverWrapper;
    @NonNull
    protected final BrowserDriverUpdater browserDriverUpdater;

    @BeforeStories
    public void updateDriver() {
        if (!customizingStories.isApiOnlyForAllStories() && !customizingStories.storyClassesContainsOnlyApiOnlyStories()) {
            browserDriverUpdater.updateDriver();
        }
    }

    @BeforeStory
    public void startStoryInteraction() {
        storyInteraction.startInteraction();
        if (!currentStory.isRestartBrowserBeforeScenario() && !currentStory.isApiOnly()) {
            webDriverWrapper.loadWebdriver();
        }
    }

    @BeforeScenario(uponType = ScenarioType.NORMAL)
    public void setupScenarioInteractionForNormal() {
        setupScenarioInteraction();
    }

    @BeforeScenario(uponType = ScenarioType.EXAMPLE)
    public void setupScenarioInteractionForExample() {
        setupScenarioInteraction();
    }

    protected void setupScenarioInteraction() {
        scenarioInteraction.startInteraction();
        storyInteraction.setScenarioInteraction(scenarioInteraction);
        scenarioInteraction.setStoryInteraction(storyInteraction);
        if (currentStory.isRestartBrowserBeforeScenario()) {
            webDriverWrapper.quit();
            webDriverWrapper.loadWebdriver();
        }
    }

    @AfterStory
    public void afterStory() {
        webDriverWrapper.quit();
    }

    @AfterStories
    public void afterStories() {
        webDriverWrapper.quit();
    }

}
