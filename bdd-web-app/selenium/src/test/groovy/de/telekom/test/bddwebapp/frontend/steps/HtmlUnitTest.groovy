package de.telekom.test.bddwebapp.frontend.steps

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper
import de.telekom.test.bddwebapp.frontend.steps.page.AnyPage
import de.telekom.test.bddwebapp.interaction.StoryInteraction
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import spock.lang.Specification

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
class HtmlUnitTest extends Specification {

    def "check if html driver is compatible with current selenium version"() {
        given:
        def driver = new HtmlUnitDriver()
        when:
        driver.get("https://www.telekom.de")
        then:
        noExceptionThrown()
        println "no class not found exception was thrown"
    }

}
