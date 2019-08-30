package de.telekom.test.bddwebapp.frontend.steps

import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper
import de.telekom.test.bddwebapp.stories.customizing.CurrentStory
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories
import spock.lang.Specification
import spock.lang.Unroll

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

    @Unroll
    def "BeforeStories leads to driver update [apiOnly=#apiOnly | containsOnlyApiStories=#containsOnlyApiStories | invocationCount=#invocationCount]"() {
        given:
        steps.customizingStories.isApiOnlyForAllStories() >> apiOnly
        steps.customizingStories.storyClassesContainsOnlyApiOnlyStories() >> containsOnlyApiStories
        when:
        steps.beforeStories()
        then:
        invocationCount * steps.browserDriverUpdater.updateDriver()
        where:
        apiOnly | containsOnlyApiStories || invocationCount
        false   | false                  || 1
        false   | true                   || 0
        true    | false                  || 0
        true    | true                   || 0
    }

    @Unroll
    def "BeforeStory loads web driver [apiOnly=#apiOnly | restartBrowser=#restartBrowser | invocationCount=#invocationCount]"() {
        given:
        steps.currentStory.isApiOnly() >> apiOnly
        steps.currentStory.isRestartBrowserBeforeScenario() >> restartBrowser
        when:
        steps.beforeStory()
        then:
        invocationCount * steps.webDriverWrapper.loadWebdriver()
        where:
        apiOnly | restartBrowser || invocationCount
        false   | false          || 1
        false   | true           || 0
        true    | false          || 0
        true    | true           || 0
    }

    def "AfterStory"() {
        when:
        steps.afterStory()
        then:
        steps.webDriverWrapper.quit()
    }

    def "AfterStories"() {
        when:
        steps.afterStories()
        then:
        steps.webDriverWrapper.quit()
    }

    def "BeforeScenario (normal)"() {
        given:
        steps.currentStory.isRestartBrowserBeforeScenario() >> restart
        when:
        steps.beforeScenarioForNormal()
        then:
        (restart ? 1 : 0) * steps.webDriverWrapper.quit()
        (restart ? 1 : 0) * steps.webDriverWrapper.loadWebdriver()
        where:
        restart | _
        true    | _
        false   | _
    }

    def "BeforeScenario (example table)"() {
        given:
        steps.currentStory.isRestartBrowserBeforeScenario() >> restart
        when:
        steps.beforeScenarioForExample()
        then:
        (restart ? 1 : 0) * steps.webDriverWrapper.quit()
        (restart ? 1 : 0) * steps.webDriverWrapper.loadWebdriver()
        where:
        restart | _
        true    | _
        false   | _
    }

}
