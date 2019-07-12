package de.telekom.test.bddwebapp.frontend.steps


import de.telekom.test.bddwebapp.frontend.lifecycle.BrowserDriverUpdater
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper
import de.telekom.test.bddwebapp.stories.customizing.CurrentStory
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories
import org.jbehave.core.annotations.ScenarioType
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

    WebDriverLifecycleSteps steps = new WebDriverLifecycleSteps(
            Mock(CurrentStory.class),
            Mock(CustomizingStories.class),
            Mock(WebDriverWrapper.class),
            Mock(BrowserDriverUpdater.class)
    )

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

    def "BeforeScenario"() {
        when:
        steps.beforeScenario(ScenarioType.EXAMPLE)
        then:
        1 * steps.currentStory.isRestartBrowserBeforeScenario() >> true
        1 * steps.webDriverWrapper.quit()
        1 * steps.webDriverWrapper.loadWebdriver()
    }

}
