package de.telekom.test.bddwebapp.api.steps

import de.telekom.test.bddwebapp.api.RequestInteractionFilter
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction
import io.restassured.RestAssured
import spock.lang.Specification

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
class RestAssuredLifecycleTest extends Specification {

    def steps = new RestAssuredLifecycle()

    def "before stories"() {
        given:
        steps.scenarioInteraction = Mock(ScenarioInteraction)
        when:
        steps.beforeStories()
        then:
        RestAssured.filters().size() == 1
        RestAssured.filters().get(0) in RequestInteractionFilter
    }

}
