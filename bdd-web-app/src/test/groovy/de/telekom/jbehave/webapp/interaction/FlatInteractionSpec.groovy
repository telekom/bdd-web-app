package de.telekom.jbehave.webapp.interaction

import de.telekom.test.bddwebapp.interaction.FlatInteraction
import spock.lang.Specification

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
    }

}
