package de.telekom.test.bddwebapp.frontend.steps

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper
import de.telekom.test.bddwebapp.interaction.StoryInteraction
import org.openqa.selenium.WebDriver
import spock.lang.Specification

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

}
