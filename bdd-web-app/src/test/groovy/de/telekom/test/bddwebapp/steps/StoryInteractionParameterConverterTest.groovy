package de.telekom.test.bddwebapp.steps

import de.telekom.test.bddwebapp.interaction.StoryInteraction
import spock.lang.Specification

class StoryInteractionParameterConverterTest extends Specification {

    StoryInteractionParameterConverter storyInteractionParameterConverter = new StoryInteractionParameterConverter(Mock(StoryInteraction.class))

    def "simple value"() {
        when:
        def value = storyInteractionParameterConverter.getValue('value')
        then:
        value == "value"
    }

    def "value from interaction key"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key') >> 'value'
        when:
        def value = storyInteractionParameterConverter.getValue('$key')
        then:
        value == "value"
    }

    def "value from interaction key with single list value"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key') >> ['value']
        when:
        def value = storyInteractionParameterConverter.getValue('$key')
        then:
        value == "value"
    }

    def "value from interaction key with list values"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key') >> ['value1', 'value2']
        when:
        def value = storyInteractionParameterConverter.getValue('$key')
        then:
        value == "value1, value2"
    }

    def "concatenated value from interaction keys"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key1') >> 'value1'
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key2') >> 'value2'
        when:
        def value = storyInteractionParameterConverter.getValue('$key1+$key2')
        then:
        value == "value1value2"
    }

    def "concatenated value from values with spaces and interaction keys"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key2') >> 'value2'
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key3') >> 'value3'
        when:
        def value = storyInteractionParameterConverter.getValue('value1 +$key2+ +$key3+ value4')
        then:
        value == "value1 value2 value3 value4"
    }

}
