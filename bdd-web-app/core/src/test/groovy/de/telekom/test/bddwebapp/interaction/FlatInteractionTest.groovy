package de.telekom.test.bddwebapp.interaction

import de.telekom.test.bddwebapp.interaction.flatinteractiontest.ComplexType
import jdk.internal.misc.Unsafe
import org.slf4j.Logger
import spock.lang.Specification

import static java.util.Arrays.asList
import static org.apache.commons.lang3.reflect.FieldUtils.*

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
class FlatInteractionTest extends Specification {

    def interaction = new FlatInteraction()

    def "start interaction"() {
        when:
        interaction.startInteraction()
        then:
        interaction.getContext() != null
    }

    def "stop interaction"() {
        when:
        interaction.stopInteraction()
        then:
        interaction.getContext() != null
    }

    def "test remember string"() {
        when:
        interaction.remember("KEY", "value")
        then:
        interaction.recall("KEY") == "value"
        interaction.recall(TestDataEnum.KEY) == "value"
    }

    def "test remember map"() {
        when:
        interaction.remember("KEY", ["key": "value"])
        then:
        interaction.recall("KEY") == ["key": "value"]
        interaction.recallMap("KEY") == ["key": "value"]
        interaction.recall("KEY.key") == "value"
        interaction.recall(TestDataEnum.KEY) == ["key": "value"]
        interaction.recallMap(TestDataEnum.KEY) == ["key": "value"]
        interaction.recallMapOrCreateNew("KEY") == ["key": "value"]
        interaction.recallMapOrCreateNew(TestDataEnum.KEY) == ["key": "value"]
    }

    def "test remember list"() {
        when:
        interaction.remember("KEY", ["value"])
        then:
        interaction.recall("KEY") == ["value"]
        interaction.recall("KEY[0]") == "value"
        interaction.recall(TestDataEnum.KEY) == ["value"]
        interaction.recallList("KEY") == ["value"]
        interaction.recallList(TestDataEnum.KEY) == ["value"]
        interaction.recallListOrCreateNew("KEY") == ["value"]
        interaction.recallListOrCreateNew(TestDataEnum.KEY) == ["value"]
    }

    def "remember values to list"() {
        when:
        interaction.rememberToList("KEY", "value1")
        interaction.rememberToList(TestDataEnum.KEY, "value2")
        then:
        interaction.recall("KEY") == ["value1", "value2"]
        interaction.recall(TestDataEnum.KEY) == ["value1", "value2"]
        interaction.recallList("KEY") == ["value1", "value2"]
        interaction.recallList(TestDataEnum.KEY) == ["value1", "value2"]
    }

    def "test remember list with map"() {
        when:
        interaction.remember("KEY", [["key": "value"]])
        then:
        interaction.recall("KEY[0].key") == "value"
        interaction.recall("KEY[0]") == ["key": "value"]
        interaction.recallList("KEY") == [["key": "value"]]
        interaction.recallList(TestDataEnum.KEY) == [["key": "value"]]
    }

    def "test remember string with object separator"() {
        when:
        interaction.remember("KEY.key", "value")
        then:
        interaction.recall("KEY.key") == "value"
    }

    def "test remember object"() {
        when:
        interaction.rememberObject(rememberWithKey, "attribute", "value")
        then:
        interaction.recall(recallWithKey) == ["attribute": "value"]
        interaction.recallNotNull(recallWithKey) == ["attribute": "value"]
        interaction.recallObject(recallWithKey, "attribute") == "value"
        interaction.recallObjectNotNull(recallWithKey, "attribute") == "value"
        interaction.recall("KEY.attribute") == "value"
        where:
        rememberWithKey  | recallWithKey
        "KEY"            | "KEY"
        "KEY"            | TestDataEnum.KEY
        TestDataEnum.KEY | "KEY"
        TestDataEnum.KEY | TestDataEnum.KEY
    }

    def "test remember object as map"() {
        when:
        interaction.rememberObject(rememberWithKey, ["attribute": "value"])
        then:
        interaction.recall(recallWithKey) == ["attribute": "value"]
        interaction.recallNotNull(recallWithKey) == ["attribute": "value"]
        interaction.recallObject(recallWithKey, "attribute") == "value"
        interaction.recallObjectNotNull(recallWithKey, "attribute") == "value"
        interaction.recall("KEY.attribute") == "value"
        where:
        rememberWithKey  | recallWithKey
        "KEY"            | "KEY"
        "KEY"            | TestDataEnum.KEY
        TestDataEnum.KEY | "KEY"
        TestDataEnum.KEY | TestDataEnum.KEY
    }

    def "test remember object with existing key object (as enum)"() {
        given:
        interaction.remember(TestDataEnum.KEY, ["attribute1": "value1"])
        when:
        interaction.rememberObject("KEY", "attribute2", "value2")
        then:
        interaction.recall("KEY") == ["attribute1": "value1", "attribute2": "value2"]
        interaction.recall("KEY.attribute2") == "value2"
        interaction.recallObject("KEY", "attribute2") == "value2"
        interaction.recall(TestDataEnum.KEY) == ["attribute1": "value1", "attribute2": "value2"]
        interaction.recallObject(TestDataEnum.KEY, "attribute2") == "value2"
    }

    def "test remember object with existing key with simple value"() {
        given:
        interaction.remember("KEY", "value")
        when:
        interaction.rememberObject("KEY", "attribute", "value2")
        then:
        interaction.recall("KEY") == "value"
        interaction.recall("KEY.attribute") == "value2"
        interaction.recallObject("KEY", "attribute") == "value2"
        interaction.recall(TestDataEnum.KEY) == "value"
        interaction.recallObject(TestDataEnum.KEY, "attribute") == "value2"
    }

    def "test recall object with hierarchy"() {
        when:
        interaction.remember("KEY", new ComplexType())
        then:
        interaction.recall(hierarchyKey) == hierarchyValue
        interaction.recall(complexKey) in ComplexType
        interaction.recall(stringKey) == stringValue

        where:
        hierarchyKey                                                        | hierarchyValue | complexKey                                                             | stringKey                                                             | stringValue
        "KEY.hierarchy"                                                     | 0              | "KEY.complexValue"                                                     | "KEY.stringValue"                                                     | "value0"
        "KEY.complexValue.hierarchy"                                        | 1              | "KEY.complexValue.complexValue"                                        | "KEY.complexValue.stringValue"                                        | "value1"
        "KEY.complexValue.complexValue.complexValue.complexValue.hierarchy" | 4              | "KEY.complexValue.complexValue.complexValue.complexValue.complexValue" | "KEY.complexValue.complexValue.complexValue.complexValue.stringValue" | "value4"
    }

    enum TestDataEnum {
        KEY
    }

}
