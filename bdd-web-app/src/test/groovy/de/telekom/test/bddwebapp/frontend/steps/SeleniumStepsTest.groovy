package de.telekom.test.bddwebapp.frontend.steps

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper
import de.telekom.test.bddwebapp.frontend.steps.page.AnyPage
import de.telekom.test.bddwebapp.frontend.steps.page.WrongPage
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

    def "create expected page"() {
        given:
        seleniumSteps.webDriverWrapper = Mock(WebDriverWrapper)
        seleniumSteps.webDriverWrapper.getDriver() >> Mock(WebDriver)
        seleniumSteps.webDriverWrapper.getDriver().getCurrentUrl() >> "https://github.com/telekom/bdd-web-app"
        seleniumSteps.storyInteraction = Mock(StoryInteraction)
        when:
        def page = seleniumSteps.createExpectedPage(AnyPage)
        then:
        page instanceof AnyPage
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

}
