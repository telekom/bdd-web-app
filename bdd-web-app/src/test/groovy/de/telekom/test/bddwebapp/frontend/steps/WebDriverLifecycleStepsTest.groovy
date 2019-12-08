package de.telekom.test.bddwebapp.frontend.steps

import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper
import de.telekom.test.bddwebapp.stories.customizing.CurrentStory
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories
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
class WebDriverLifecycleStepsTest extends Specification {

    def steps = new WebDriverLifecycleSteps()

    def "setup"() {
        steps.currentStory = Mock(CurrentStory)
        steps.customizingStories = Mock(CustomizingStories)
        steps.webDriverWrapper = Mock(WebDriverWrapper)
        steps.browserDriverUpdater = Mock(BrowserDriverUpdater)
    }

    def "quit browser after every story"() {
        when:
        steps.quitBrowserAfterStory()
        then:
        steps.webDriverWrapper.quit()
    }

    def "quit browser after execution of the stories"() {
        when:
        steps.quitBrowserAfterStories()
        then:
        steps.webDriverWrapper.quit()
    }

    def "quit browser after scenario"() {
        given:
        steps.currentStory.isRestartBrowserBeforeScenario() >> restart
        when:
        steps.quitBrowserAfterScenario()
        then:
        (restart ? 1 : 0) * steps.webDriverWrapper.quit()
        where:
        restart | _
        true    | _
        false   | _
    }

}
