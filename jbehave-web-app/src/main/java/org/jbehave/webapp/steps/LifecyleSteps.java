package org.jbehave.webapp.steps;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jbehave.core.annotations.*;
import org.jbehave.webapp.frontend.lifecycle.BrowserDriverUpdater;
import org.jbehave.webapp.frontend.lifecycle.WebDriverWrapper;
import org.jbehave.webapp.interaction.ScenarioInteraction;
import org.jbehave.webapp.interaction.StoryInteraction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss
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
        storyInteraction.startInteraction();
        webDriverWrapper.loadWebdriver();
    }

    @AfterStory
    public void clearStoryInteraction() {
        storyInteraction.stopInteraction();
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
        storyInteraction.setSequenceInteraction(scenarioInteraction);
    }

    @AfterScenario(uponType = ScenarioType.NORMAL)
    public void clearSequenceInteractionNormal() {
        scenarioInteraction.stopInteraction();
    }

    @AfterScenario(uponType = ScenarioType.EXAMPLE)
    public void clearSequenceInteractionExample() {
        clearSequenceInteractionNormal();
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
            return storyInteraction.recallNotNull(possibleStoryInteractionKeyOrValue.substring(1));
        }
        return possibleStoryInteractionKeyOrValue; // regular test value
    }

}
