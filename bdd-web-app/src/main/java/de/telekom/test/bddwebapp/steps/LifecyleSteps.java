package de.telekom.test.bddwebapp.steps;

import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.stories.customizing.CurrentStory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jbehave.core.annotations.*;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

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
    protected final WebDriverWrapper webDriverWrapper;
    @NonNull
    protected final BrowserDriverUpdater browserDriverUpdater;
    @NonNull
    protected final StoryInteractionParameterConverter storyInteractionParameterConverter;

    @BeforeStories
    public void updateDriver() {
        browserDriverUpdater.updateDriver();
    }

    @BeforeStory
    public void startStoryInteraction() {
        storyInteraction.startInteraction();
        if (!currentStory.isApiOnly()) {
            webDriverWrapper.loadWebdriver();
        }
    }

    @BeforeScenario(uponType = ScenarioType.NORMAL)
    public void setupSequenceInteractionForNormal() {
        setupSequenceInteraction();
    }

    @BeforeScenario(uponType = ScenarioType.EXAMPLE)
    public void setupSequenceInteractionForExample() {
        setupSequenceInteraction();
    }

    private void setupSequenceInteraction() {
        scenarioInteraction.startInteraction();
        storyInteraction.setScenarioInteraction(scenarioInteraction);
        scenarioInteraction.setStoryInteraction(storyInteraction);
    }

    @AfterStory
    public void afterStory() {
        if (!currentStory.isApiOnly()) {
            webDriverWrapper.quit();
        }
    }

    @AfterStories
    public void afterStories() {
        webDriverWrapper.quit();
    }

    @AsParameterConverter
    public String checkForStoryInteractionKeyAndGetValue(String possibleStoryInteractionKeyOrValue) {
        return storyInteractionParameterConverter.getValue(possibleStoryInteractionKeyOrValue);
    }

    public List<Map<String, String>> getRowsWithInteractionKey(ExamplesTable examplesTable) {
        List<Map<String, String>> rows = examplesTable.getRows();
        rows.forEach(map -> map.entrySet().forEach(entry -> entry.setValue(checkForStoryInteractionKeyAndGetValue(entry.getValue()))));
        return rows;
    }

}
