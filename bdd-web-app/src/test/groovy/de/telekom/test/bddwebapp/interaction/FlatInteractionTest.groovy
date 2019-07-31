package de.telekom.test.bddwebapp.interaction

import org.slf4j.Logger
import spock.lang.Specification

import static java.util.Arrays.asList
import static org.apache.commons.lang3.reflect.FieldUtils.*

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

    def interaction = new FlatInteraction()

    def log = Mock(Logger)

    def setup() {
        // getField(abstractInteraction.class, "log") doesn't work here
        def logField = asList(getAllFields(interaction.class)).stream()
                .filter({ field -> field.getType().isAssignableFrom(Logger.class) })
                .findFirst().get()
        removeFinalModifier(logField)
        writeStaticField(logField, log, true)
    }

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

    def "recall integer in all flavors"() {
        when:
        interaction.context.put("KEY", 1)
        then:
        interaction.recall("KEY") == 1
        interaction.recall(TestDataEnum.KEY) == 1
        interaction.recall("KEY", Integer.class) == 1
        interaction.recall(TestDataEnum.KEY, Integer.class) == 1
        interaction.recallNotNull("KEY") == 1
        interaction.recallNotNull(TestDataEnum.KEY) == 1
        interaction.recallNotNull("KEY", Integer.class) == 1
        interaction.recallNotNull(TestDataEnum.KEY, Integer.class) == 1
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
    }

    def "test remember list"() {
        when:
        interaction.remember("KEY", ["value"])
        then:
        interaction.recall("KEY") == ["value"]
        interaction.recallList("KEY") == ["value"]
        interaction.recall("KEY[0]") == "value"
        interaction.recall(TestDataEnum.KEY) == ["value"]
        interaction.recallList(TestDataEnum.KEY) == ["value"]
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
        interaction.rememberObject("KEY", "attribute", "value")
        then:
        interaction.recall("KEY") == ["attribute": "value"]
        interaction.recall("KEY.attribute") == "value"
        interaction.recallObject("KEY", "attribute") == "value"
        interaction.recall(TestDataEnum.KEY) == ["attribute": "value"]
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

    def "log all possible keys with value"() {
        given:
        interaction.remember("KEY", [["key": "value"]])
        when:
        interaction.logAllPossibleKeysWithValue()
        then:
        1 * log.info("Log all possible keys with value:")
        1 * log.info("KEY[0]={key=value},\nKEY[0].key=value,\nKEY=[{key=value}]")
    }

    def "log all possible keys with type"() {
        given:
        interaction.remember("KEY", [["key": "value"]])
        when:
        interaction.logAllPossibleKeysWithType()
        then:
        1 * log.info("Log all possible keys with type:")
        1 * log.info("KEY[0]=class java.util.LinkedHashMap,\nKEY[0].key=class java.lang.String,\nKEY=class java.util.ArrayList")
    }

    enum TestDataEnum {
        KEY
    }

}
