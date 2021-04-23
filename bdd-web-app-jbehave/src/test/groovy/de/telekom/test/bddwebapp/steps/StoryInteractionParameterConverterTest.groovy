package de.telekom.test.bddwebapp.steps

import de.telekom.test.bddwebapp.interaction.StoryInteraction
import de.telekom.test.bddwebapp.jbehave.steps.StoryInteractionParameterConverter
import org.jbehave.core.model.ExamplesTable
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
class StoryInteractionParameterConverterTest extends Specification {

    def storyInteractionParameterConverter = new StoryInteractionParameterConverter()

    def setup() {
        storyInteractionParameterConverter.storyInteraction = Mock(StoryInteraction)
    }

    def "simple value"() {
        when:
        def value = storyInteractionParameterConverter.getValueFromKeyOrValueOrConcatenated('value')
        then:
        value == "value"
    }

    def "value from interaction key"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key') >> 'value'
        when:
        def value = storyInteractionParameterConverter.getValueFromKeyOrValueOrConcatenated('$key')
        then:
        value == "value"
    }

    def "value from interaction key with single list value"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key') >> ['value']
        when:
        def value = storyInteractionParameterConverter.getValueFromKeyOrValueOrConcatenated('$key')
        then:
        value == "value"
    }

    def "value from interaction key with list values"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key') >> ['value1', 'value2']
        when:
        def value = storyInteractionParameterConverter.getValueFromKeyOrValueOrConcatenated('$key')
        then:
        value == "value1, value2"
    }

    def "concatenated value from interaction keys"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key1') >> 'value1'
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key2') >> 'value2'
        when:
        def value = storyInteractionParameterConverter.getValueFromKeyOrValueOrConcatenated('$key1+$key2')
        then:
        value == "value1value2"
    }

    def "concatenated value from values with spaces and interaction keys"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key2') >> 'value2'
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key3') >> 'value3'
        when:
        def value = storyInteractionParameterConverter.getValueFromKeyOrValueOrConcatenated('value1 +$key2+ +$key3+ value4')
        then:
        value == "value1 value2 value3 value4"
    }

    def "get rows with interaction values"() {
        given:
        storyInteractionParameterConverter.storyInteraction.recallNotNull('key') >> 'value2'
        ExamplesTable examplesTable = Mock(ExamplesTable)
        examplesTable.getRows() >> [['attribute': 'value'], ['attribute': '$key']]
        when:
        def rows = storyInteractionParameterConverter.getRowsWithInteractionKey(examplesTable)
        then:
        rows == [['attribute': 'value'], ['attribute': 'value2']]
    }

}
