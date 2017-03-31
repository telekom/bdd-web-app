package de.telekom.test.steps;

import de.telekom.test.interaction.ScenarioInteraction;
import de.telekom.test.frontend.selenium.WebDriverWrapper;
import de.telekom.test.interaction.StoryInteraction;
import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 * <p>
 * Created by d.keiss on 18.08.2016.
 */
@Steps
public class LifecyleSteps {

	/*
	 * The current selenium page of story interaction. Is automatically deleted after a story.
	 */
	public static final String CURRENT_PAGE = "CURRENT_PAGE";

	@Autowired
	protected ScenarioInteraction scenarioInteraction;

	@Autowired
	protected StoryInteraction storyInteraction;

	@Autowired
	private WebDriverWrapper webDriverWrapper;

	@BeforeStory
	public void startStoryInteraction() {
		storyInteraction.startInteraction();
		webDriverWrapper.initialize();
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
