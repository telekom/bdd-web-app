package de.telekom.test.bddwebapp.interaction

import de.telekom.test.bddwebapp.api.RequestBuilder
import spock.lang.Specification

class ScenarioInteractionTest extends Specification {

    def scenarioInteraction = new ScenarioInteraction()

    def setup() {
        scenarioInteraction.storyInteraction = new StoryInteraction()
        scenarioInteraction.requestBuilder = Mock(RequestBuilder.class)
    }

    def "start interaction"() {
        when:
        scenarioInteraction.startInteraction()
        then:
        1 * scenarioInteraction.requestBuilder.clearRequest()
    }

    def "create new array body"() {
        when:
        def body = scenarioInteraction.arrayBody()
        then:
        body == []
        scenarioInteraction.getContext().get(ScenarioInteraction.BODY) == []
    }

    def "get array body"() {
        given:
        scenarioInteraction.getContext().put(ScenarioInteraction.BODY, ["value1", "value2"])
        when:
        def body = scenarioInteraction.arrayBody()
        then:
        body == ["value1", "value2"]
    }

    def "create new map body"() {
        when:
        def body = scenarioInteraction.mapBody()
        then:
        body == [:]
        scenarioInteraction.getContext().get(ScenarioInteraction.BODY) == [:]
    }

    def "get map body"() {
        given:
        scenarioInteraction.getContext().put(ScenarioInteraction.BODY, ["key": "value"])
        when:
        def body = scenarioInteraction.mapBody()
        then:
        body == ["key": "value"]
    }

    def "create new map path param"() {
        when:
        def body = scenarioInteraction.mapPathParam()
        then:
        body == [:]
        scenarioInteraction.getContext().get(ScenarioInteraction.PATH_PARAMS) == [:]
    }

    def "get map path param"() {
        given:
        scenarioInteraction.getContext().put(ScenarioInteraction.PATH_PARAMS, ["key": "value"])
        when:
        def body = scenarioInteraction.mapPathParam()
        then:
        body == ["key": "value"]
    }

    def "create new map query param"() {
        when:
        def body = scenarioInteraction.mapQueryParam()
        then:
        body == [:]
        scenarioInteraction.getContext().get(ScenarioInteraction.QUERY_PARAMS) == [:]
    }

    def "get map query param"() {
        given:
        scenarioInteraction.getContext().put(ScenarioInteraction.QUERY_PARAMS, ["key": "value"])
        when:
        def body = scenarioInteraction.mapQueryParam()
        then:
        body == ["key": "value"]
    }

    def "remember from story interaction without available key"() {
        when:
        scenarioInteraction.rememberFromStoryInteraction("key")
        then:
        thrown(AssertionError)
    }

    def "remember from story interaction"() {
        given:
        scenarioInteraction.storyInteraction.remember("key", "value")
        when:
        scenarioInteraction.rememberFromStoryInteraction("key")
        then:
        scenarioInteraction.recall("key") == "value"
    }

    def "remember object from story interaction without available key"() {
        when:
        scenarioInteraction.rememberObjectFromStoryInteraction("key", "object")
        then:
        thrown(AssertionError)
    }

    def "remember object from story interaction"() {
        given:
        scenarioInteraction.storyInteraction.rememberObject("key", "object", "value")
        when:
        scenarioInteraction.rememberObjectFromStoryInteraction("key", "object")
        then:
        scenarioInteraction.recallObject("key", "object") == "value"
    }

}
