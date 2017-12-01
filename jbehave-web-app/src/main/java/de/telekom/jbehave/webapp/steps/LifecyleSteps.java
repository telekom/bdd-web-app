package de.telekom.jbehave.webapp.steps;

import de.telekom.jbehave.webapp.frontend.lifecycle.BrowserDriverUpdater;
import de.telekom.jbehave.webapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.jbehave.webapp.interaction.ScenarioInteraction;
import de.telekom.jbehave.webapp.interaction.StoryInteraction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Steps
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LifecyleSteps {

    /*
     * The current selenium page of story interaction. Is automatically deleted after a story.
     */
    public static final String CURRENT_PAGE = "CURRENT_PAGE";

    protected final @NonNull
    ScenarioInteraction scenarioInteraction;
    protected final @NonNull
    StoryInteraction storyInteraction;

    private final @NonNull
    WebDriverWrapper webDriverWrapper;
    private final @NonNull
    BrowserDriverUpdater browserDriverUpdater;

    @BeforeStories
    public void updateDriver() {
        browserDriverUpdater.updateDriver();
    }

    @BeforeStory
    public void startStoryInteraction() {
        storyInteraction.stopInteraction();
        storyInteraction.startInteraction();
        webDriverWrapper.loadWebdriver();
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
        scenarioInteraction.stopInteraction();
        scenarioInteraction.startInteraction();
        storyInteraction.setSequenceInteraction(scenarioInteraction);
        scenarioInteraction.setStoryInteraction(storyInteraction);
    }

    @AfterStory
    public void afterStory() {
        webDriverWrapper.quit();
    }

    @AfterStories
    public void afterStories() {
        webDriverWrapper.quit();
    }

    @AsParameterConverter
    public String checkForStoryInteractionKeyAndGetValue(String possibleStoryInteractionKeyOrValue) {
        if (possibleStoryInteractionKeyOrValue.startsWith("$")) {
            return storyInteraction.recallNotNull(possibleStoryInteractionKeyOrValue.substring(1)).toString();
        }
        return possibleStoryInteractionKeyOrValue; // regular test value
    }

}
