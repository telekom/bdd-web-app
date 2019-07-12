package de.telekom.test.bddwebapp.interaction

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
class FlatInteractionTest extends Specification {

    FlatInteraction abstractInteraction = new FlatInteraction() {
    }

    enum TestDataEnum {
        KEY
    }

    def "test remember string"() {
        when:
        abstractInteraction.remember("KEY", "value")
        then:
        abstractInteraction.recall("KEY") == "value"
        abstractInteraction.recall(TestDataEnum.KEY) == "value"
    }

    def "test remember map"() {
        when:
        abstractInteraction.remember("KEY", ["key": "value"])
        then:
        abstractInteraction.recall("KEY") == ["key": "value"]
        abstractInteraction.recallMap("KEY") == ["key": "value"]
        abstractInteraction.recall("KEY.key") == "value"
        abstractInteraction.recall(TestDataEnum.KEY) == ["key": "value"]
        abstractInteraction.recallMap(TestDataEnum.KEY) == ["key": "value"]
    }

    def "test remember list"() {
        when:
        abstractInteraction.remember("KEY", ["value"])
        then:
        abstractInteraction.recall("KEY") == ["value"]
        abstractInteraction.recallList("KEY") == ["value"]
        abstractInteraction.recall("KEY[0]") == "value"
        abstractInteraction.recall(TestDataEnum.KEY) == ["value"]
        abstractInteraction.recallList(TestDataEnum.KEY) == ["value"]
    }

    def "test remember list with map"() {
        when:
        abstractInteraction.remember("KEY", [["key": "value"]])
        then:
        abstractInteraction.recall("KEY[0].key") == "value"
        abstractInteraction.recall("KEY[0]") == ["key": "value"]
        abstractInteraction.recallList("KEY") == [["key": "value"]]
        abstractInteraction.recallList(TestDataEnum.KEY) == [["key": "value"]]
    }

    def "test remember string with object separator"() {
        when:
        abstractInteraction.remember("KEY.key", "value")
        then:
        abstractInteraction.recall("KEY.key") == "value"
    }

    def "test remember object"() {
        when:
        abstractInteraction.rememberObject("KEY", "attribute", "value")
        then:
        abstractInteraction.recall("KEY") == ["attribute": "value"]
        abstractInteraction.recall("KEY.attribute") == "value"
        abstractInteraction.recallObject("KEY", "attribute") == "value"
        abstractInteraction.recall(TestDataEnum.KEY) == ["attribute": "value"]
    }

    def "test remember object with existing key object (as enum)"() {
        given:
        abstractInteraction.remember(TestDataEnum.KEY, ["attribute1": "value1"])
        when:
        abstractInteraction.rememberObject("KEY", "attribute2", "value2")
        then:
        abstractInteraction.recall("KEY") == ["attribute1": "value1", "attribute2": "value2"]
        abstractInteraction.recall("KEY.attribute2") == "value2"
        abstractInteraction.recallObject("KEY", "attribute2") == "value2"
        abstractInteraction.recall(TestDataEnum.KEY) == ["attribute1": "value1", "attribute2": "value2"]
        abstractInteraction.recallObject(TestDataEnum.KEY, "attribute2") == "value2"
    }

    def "test remember object with existing key with simple value"() {
        given:
        abstractInteraction.remember("KEY", "value")
        when:
        abstractInteraction.rememberObject("KEY", "attribute", "value2")
        then:
        abstractInteraction.recall("KEY") == "value"
        abstractInteraction.recall("KEY.attribute") == "value2"
        abstractInteraction.recallObject("KEY", "attribute") == "value2"
        abstractInteraction.recall(TestDataEnum.KEY) == "value"
        abstractInteraction.recallObject(TestDataEnum.KEY, "attribute") == "value2"
    }


}
