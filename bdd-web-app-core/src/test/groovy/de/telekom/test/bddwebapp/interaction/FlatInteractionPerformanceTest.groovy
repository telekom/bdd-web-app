package de.telekom.test.bddwebapp.interaction


import spock.lang.Ignore
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
@Ignore
class FlatInteractionPerformanceTest extends Specification {

    FlatInteraction abstractInteraction = new FlatInteraction() {
    }

    Map exampleJson = json(20)

    def "save simple map with list"() {
        given:
        println "Given items: " + _count(exampleJson)
        when:
        Date start = new Date()
        abstractInteraction.remember("key", exampleJson)
        println "Remember duration in ms: " + duration(start)
        start = new Date()
        def value = abstractInteraction.recall("key.key0")
        println "Recall duration in ms: " + duration(start)
        then:
        println "Recall key.key0 value " + value
        value == "value0"
        println "Items after remember: " + _count(abstractInteraction.getContext())
    }

    def "remember deep map"() {
        given:
        Map<String, Object> complexTestdata = _complexMap(10, new HashMap<>())
        println "Given items: " + _count(complexTestdata)
        def key = itemKeyForValue0(10)
        when:
        Date start = new Date()
        abstractInteraction.remember("key", complexTestdata)
        println "Remember duration in ms: " + duration(start)
        start = new Date()
        def value = abstractInteraction.recall(key)
        println "Recall duration in ms: " + duration(start)
        then:
        println "Recall " + key + " value " + value
        value == "value0"
        println "Items after remember: " + _count(abstractInteraction.getContext())
    }

    def "remember very deep map"() {
        given:
        Map<String, Object> complexTestdata = _complexMap(1000, new HashMap<>())
        println "Given items: " + _count(complexTestdata)
        def key = itemKeyForValue0(1000)
        when:
        Date start = new Date()
        abstractInteraction.remember("key", complexTestdata)
        println "Remember duration in ms: " + duration(start)
        start = new Date()
        def value = abstractInteraction.recall(key)
        println "Recall duration in ms: " + duration(start)
        then:
        println "Recall " + key + " value " + value
        value == "value0"
        println "Items after remember: " + _count(abstractInteraction.getContext())
    }

    Map<String, Object> _complexMap(int deep, Map<String, Object> complexTestdata) {
        if (deep == 0) {
            return exampleJson
        }
        complexTestdata.put(("key" + deep), [_complexMap(--deep, new HashMap<String, Object>(complexTestdata))])
        return complexTestdata
    }

    int _count(Map<String, Object> testData) {
        int count = 0
        for (Object value : testData.values()) {
            if (value instanceof Map) {
                count += _count(value)
                continue
            }
            if (value instanceof List) {
                for (Object item : value) {
                    count += _count(item)
                }
            }
            count++
        }
        return count
    }

    Map json(int jsonElements) {
        Map json = new HashMap()
        for (int i = 0; i < jsonElements; i++) {
            json.put("key" + i, "value" + i)
        }
        return json
    }

    String itemKeyForValue0(int deep) {
        String key = "key"
        for (int i = deep; i > 0; i--) {
            key += ".key" + i + "[0]"
        }
        key += ".key0"
        return key
    }

    Long duration(Date start) {
        return new Date().getTime() - start.getTime()
    }

}
