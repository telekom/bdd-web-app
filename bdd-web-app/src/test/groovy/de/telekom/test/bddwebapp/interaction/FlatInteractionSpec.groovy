package de.telekom.test.bddwebapp.interaction

import spock.lang.Specification

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
class FlatInteractionSpec extends Specification {

    FlatInteraction abstractInteraction = new FlatInteraction() {
    }

    def "test remember string"() {
        when:
        abstractInteraction.remember("key", "value")
        then:
        abstractInteraction.recall("key") == "value"
    }

    def "test remember string with object separator"() {
        when:
        abstractInteraction.remember("key.key", "value")
        then:
        abstractInteraction.recall("key.key") == "value"
    }

    def "test remember map"() {
        when:
        abstractInteraction.remember("key", ["key": "value"])
        then:
        abstractInteraction.recall("key") == ["key": "value"]
        abstractInteraction.recallMap("key") == ["key": "value"]
        abstractInteraction.recall("key.key") == "value"
    }

    def "test remember list"() {
        when:
        abstractInteraction.remember("key", ["value"])
        then:
        abstractInteraction.recall("key") == ["value"]
        abstractInteraction.recallList("key") == ["value"]
        abstractInteraction.recall("key[0]") == "value"
    }

    def "test remember list with map"() {
        when:
        abstractInteraction.remember("key", [["key": "value"]])
        then:
        abstractInteraction.recall("key[0].key") == "value"
        abstractInteraction.recall("key[0]") == ["key": "value"]
        abstractInteraction.recallList("key") == [["key": "value"]]
    }

    def "test remember object"() {
        when:
        abstractInteraction.rememberObject("key", "attribute", "value")
        then:
        abstractInteraction.recall("key") == ["attribute": "value"]
        abstractInteraction.recall("key.attribute") == "value"
        abstractInteraction.recallObject("key", "attribute") == "value"
    }

    def "test remember object with existing key object"() {
        given:
        abstractInteraction.remember("key", ["attribute1": "value1"])
        when:
        abstractInteraction.rememberObject("key", "attribute2", "value2")
        then:
        abstractInteraction.recall("key") == ["attribute1": "value1", "attribute2": "value2"]
        abstractInteraction.recall("key.attribute2") == "value2"
        abstractInteraction.recallObject("key", "attribute2") == "value2"
    }

    def "test remember object with existing key with simple value"() {
        given:
        abstractInteraction.remember("key", "value")
        when:
        abstractInteraction.rememberObject("key", "attribute", "value2")
        then:
        abstractInteraction.recall("key") == "value"
        abstractInteraction.recall("key.attribute") == "value2"
        abstractInteraction.recallObject("key", "attribute") == "value2"
    }

}
