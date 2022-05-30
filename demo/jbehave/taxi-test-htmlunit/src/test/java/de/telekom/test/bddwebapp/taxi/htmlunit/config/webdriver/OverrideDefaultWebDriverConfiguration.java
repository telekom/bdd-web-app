package de.telekom.test.bddwebapp.taxi.htmlunit.config.webdriver;

import de.telekom.test.bddwebapp.frontend.lifecycle.UsefulWebDriverConfiguration;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Component;

/**
 * Overwrite the default configuration and add a new behaviour after browser load.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class OverrideDefaultWebDriverConfiguration extends UsefulWebDriverConfiguration {

    public OverrideDefaultWebDriverConfiguration() {
        WebDriverWrapper.DEFAULT_WEB_DRIVER_CONFIGURATION = OverrideDefaultWebDriverConfiguration.class;
    }

    public WebDriver loadHtmlUnit() {
        return new HtmlUnitDriver(true);
    }

}
