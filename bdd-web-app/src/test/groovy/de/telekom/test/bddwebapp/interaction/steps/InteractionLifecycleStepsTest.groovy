package de.telekom.test.bddwebapp.interaction.steps

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction
import de.telekom.test.bddwebapp.interaction.StoryInteraction
import spock.lang.Specification

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
class InteractionLifecycleStepsTest extends Specification {

    def steps = new InteractionLifecycleSteps(
            Mock(ScenarioInteraction.class),
            Mock(StoryInteraction.class))

    def "before story"() {
        when:
        steps.beforeStory()
        then:
        1 * steps.storyInteraction.startInteraction()
    }

    def "before scenario for type normal"() {
        when:
        steps.beforeScenarioForNormal()
        then:
        1 * steps.scenarioInteraction.startInteraction()
        1 * steps.storyInteraction.setScenarioInteraction(steps.scenarioInteraction)
        1 * steps.scenarioInteraction.setStoryInteraction(steps.storyInteraction)
    }

    def "before scenario for type example"() {
        when:
        steps.beforeScenarioForExample()
        then:
        1 * steps.scenarioInteraction.startInteraction()
        1 * steps.storyInteraction.setScenarioInteraction(steps.scenarioInteraction)
        1 * steps.scenarioInteraction.setStoryInteraction(steps.storyInteraction)
    }

}
