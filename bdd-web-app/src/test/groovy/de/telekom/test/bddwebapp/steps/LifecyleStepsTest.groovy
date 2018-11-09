package de.telekom.test.bddwebapp.steps

import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction
import de.telekom.test.bddwebapp.interaction.StoryInteraction
import de.telekom.test.bddwebapp.stories.customizing.CurrentStory
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories
import spock.lang.Specification

class LifecyleStepsTest extends Specification {

    LifecyleSteps lifecyleSteps = new LifecyleSteps(
            Mock(ScenarioInteraction.class),
            Mock(StoryInteraction.class),
            Mock(CurrentStory.class),
            Mock(CustomizingStories.class),
            Mock(WebDriverWrapper.class),
            Mock(BrowserDriverUpdater.class))

    def "frontend stories"() {
        given:
        lifecyleSteps.currentStory.isApiOnly() >> false
        lifecyleSteps.customizingStories.isApiOnlyForAllStories() >> false
        when:
        lifecyleSteps.updateDriver()
        lifecyleSteps.startStoryInteraction()
        lifecyleSteps.setupSequenceInteractionForNormal()
        lifecyleSteps.afterStory()
        lifecyleSteps.afterStories()
        then:
        1 * lifecyleSteps.browserDriverUpdater.updateDriver()
        1 * lifecyleSteps.storyInteraction.startInteraction()
        1 * lifecyleSteps.webDriverWrapper.loadWebdriver()
        1 * lifecyleSteps.scenarioInteraction.startInteraction()
        1 * lifecyleSteps.storyInteraction.setScenarioInteraction(_)
        1 * lifecyleSteps.scenarioInteraction.setStoryInteraction(_)
        2 * lifecyleSteps.webDriverWrapper.quit()
    }

    def "api only for all stories"() {
        given:
        lifecyleSteps.currentStory.isApiOnly() >> true
        lifecyleSteps.customizingStories.isApiOnlyForAllStories() >> true
        when:
        lifecyleSteps.updateDriver()
        lifecyleSteps.startStoryInteraction()
        lifecyleSteps.setupSequenceInteractionForNormal()
        lifecyleSteps.afterStory()
        lifecyleSteps.afterStories()
        then:
        0 * lifecyleSteps.browserDriverUpdater.updateDriver()
        1 * lifecyleSteps.storyInteraction.startInteraction()
        0 * lifecyleSteps.webDriverWrapper.loadWebdriver()
        1 * lifecyleSteps.scenarioInteraction.startInteraction()
        1 * lifecyleSteps.storyInteraction.setScenarioInteraction(_)
        1 * lifecyleSteps.scenarioInteraction.setStoryInteraction(_)
        2 * lifecyleSteps.webDriverWrapper.quit()
    }

    def "api only for current story"() {
        given:
        lifecyleSteps.currentStory.isApiOnly() >> true
        lifecyleSteps.customizingStories.isApiOnlyForAllStories() >> false
        when:
        lifecyleSteps.updateDriver()
        lifecyleSteps.startStoryInteraction()
        lifecyleSteps.setupSequenceInteractionForNormal()
        lifecyleSteps.afterStory()
        lifecyleSteps.afterStories()
        then:
        1 * lifecyleSteps.browserDriverUpdater.updateDriver()
        1 * lifecyleSteps.storyInteraction.startInteraction()
        0 * lifecyleSteps.webDriverWrapper.loadWebdriver()
        1 * lifecyleSteps.scenarioInteraction.startInteraction()
        1 * lifecyleSteps.storyInteraction.setScenarioInteraction(_)
        1 * lifecyleSteps.scenarioInteraction.setStoryInteraction(_)
        2 * lifecyleSteps.webDriverWrapper.quit()
    }

}
