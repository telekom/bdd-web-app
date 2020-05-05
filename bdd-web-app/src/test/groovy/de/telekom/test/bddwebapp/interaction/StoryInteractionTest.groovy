package de.telekom.test.bddwebapp.interaction

import spock.lang.Specification

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
class StoryInteractionTest extends Specification {

    def storyInteraction = new StoryInteraction()

    void setup() {
        storyInteraction.scenarioInteraction = new ScenarioInteraction()
    }

    def "remember from scenario interaction without available key"() {
        when:
        storyInteraction.rememberFromScenarioInteraction("key")
        then:
        thrown(AssertionError)
    }

    def "remember from scenario interaction"() {
        given:
        storyInteraction.scenarioInteraction.remember("key", "value")
        when:
        storyInteraction.rememberFromScenarioInteraction("key")
        then:
        storyInteraction.recall("key") == "value"
    }

    def "remember object from scenario interaction without available key"() {
        when:
        storyInteraction.rememberObjectFromScenarioInteraction("key", "object")
        then:
        thrown(AssertionError)
    }

    def "remember object from scenario interaction"() {
        given:
        storyInteraction.scenarioInteraction.rememberObject("key", "object", "value")
        when:
        storyInteraction.rememberObjectFromScenarioInteraction("key", "object")
        then:
        storyInteraction.recallObject("key", "object") == "value"
    }

}
