package de.telekom.jbehave.webapp.interaction

import spock.lang.Specification

class FlatInteractionSpec extends Specification {

    FlatInteraction abstractInteraction = new FlatInteraction() {
    }

    def "test recall string"() {
        given:
        abstractInteraction.remember("key", "value")
        when:
        String value = abstractInteraction.recall("key")
        then:
        value == "value"
    }

    def "test recall string with object separator"() {
        given:
        abstractInteraction.remember("key.key", "value")
        when:
        String value = abstractInteraction.recall("key.key")
        then:
        value == "value"
    }

    def "test recall map"() {
        given:
        abstractInteraction.remember("key", ["key": "value"])
        when:
        Map<String, String> value = abstractInteraction.recall("key")
        then:
        value == ["key": "value"]
    }

    def "test recall map with special method"() {
        given:
        abstractInteraction.remember("key", ["key": "value"])
        when:
        Map<String, String> value = abstractInteraction.recallMap("key")
        then:
        value == ["key": "value"]
    }

    def "test recall string from map"() {
        given:
        abstractInteraction.remember("key", ["key": "value"])
        when:
        String value = abstractInteraction.recall("key.key")
        then:
        value == "value"
    }

    def "test recall list value"() {
        given:
        abstractInteraction.remember("key", ["value"])
        when:
        String value = abstractInteraction.recall("key[0]")
        then:
        value == "value"
    }

    def "test recall string from map in list"() {
        given:
        abstractInteraction.remember("key", [["key": "value"]])
        when:
        String value = abstractInteraction.recall("key[0].key")
        then:
        value == "value"
    }

    def "test recall list"() {
        given:
        abstractInteraction.remember("key", ["value"])
        when:
        List<String> value = abstractInteraction.recall("key")
        then:
        value == ["value"]
    }

    def "test recall list with special method"() {
        given:
        abstractInteraction.remember("key", ["value"])
        when:
        List<String> value = abstractInteraction.recallList("key")
        then:
        value == ["value"]
    }

}
