package de.telekom.test.bddwebapp.frontend.steps

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper
import de.telekom.test.bddwebapp.frontend.steps.page.AnyPage
import de.telekom.test.bddwebapp.frontend.steps.page.WrongPage
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction
import de.telekom.test.bddwebapp.interaction.StoryInteraction
import org.openqa.selenium.WebDriver
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
class SeleniumStepsTest extends Specification {

    def seleniumSteps = new SeleniumSteps() {
    }

    void setup() {
    }

    def "create expected page with different urls"() {
        given:
        seleniumSteps.webDriverWrapper = Mock(WebDriverWrapper)
        seleniumSteps.webDriverWrapper.getDriver() >> Mock(WebDriver)
        seleniumSteps.webDriverWrapper.getDriver().getCurrentUrl() >> "https://github.com/telekom/bdd-web-app?key=value"
        seleniumSteps.storyInteraction = Mock(StoryInteraction)
        AnyPage.URL = url
        when:
        def page = seleniumSteps.createExpectedPage(AnyPage)
        then:
        page instanceof AnyPage

        where:
        url                                                  | _
        "https://github.com/telekom/bdd-web-app"             | _
        "https://github.com/telekom/bdd-web-app\\?key=value" | _
        "telekom/bdd-web-app"                                | _
        ".+/bdd-web-app"                                     | _
        ".+/bdd-web-app\\?key=.+"                            | _
    }

    def "create page with wrong url"() {
        given:
        seleniumSteps.webDriverWrapper = Mock(WebDriverWrapper)
        seleniumSteps.webDriverWrapper.getDriver() >> Mock(WebDriver)
        seleniumSteps.webDriverWrapper.getDriver().getCurrentUrl() >> "https://github.com/telekom/other-framework"
        seleniumSteps.storyInteraction = Mock(StoryInteraction)
        when:
        seleniumSteps.createExpectedPage(AnyPage)
        then:
        thrown RuntimeException
    }

    def "create expected page with exceptions"() {
        when:
        seleniumSteps.createExpectedPage(WrongPage.class)
        then:
        thrown RuntimeException
    }

    def "get current page"() {
        given:
        seleniumSteps.storyInteraction = Mock(StoryInteraction)
        seleniumSteps.storyInteraction.recall("CURRENT_PAGE") >> new AnyPage()
        when:
        def currentPage = seleniumSteps.getCurrentPage()
        then:
        currentPage instanceof AnyPage
    }

    def "open url"() {
        given:
        seleniumSteps.webDriverWrapper = Mock(WebDriverWrapper.class)
        seleniumSteps.webDriverWrapper.getDriver() >> Mock(WebDriver.class)
        when:
        seleniumSteps.open("url")
        then:
        1 * seleniumSteps.webDriverWrapper.getDriver().get("url")
    }

    def "map query param"() {
        given:
        seleniumSteps.scenarioInteraction = Mock(ScenarioInteraction.class)
        seleniumSteps.scenarioInteraction.recallMapOrCreateNew(SeleniumSteps.QUERY_PARAMS) >> ["key": "value"]
        when:
        def queryParams = seleniumSteps.mapQueryParam()
        then:
        queryParams == ["key": "value"]
    }

}