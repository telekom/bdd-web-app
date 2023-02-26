package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.taxi.customizing.config.webdriver.NoJsWebDriverConfiguration;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class WebDriverConfigurationSteps {

    @Autowired
    private WebDriverWrapper webDriverWrapper;

    @Before("@noJsWebDriverConfiguration")
    public void noJsWebDriverConfiguration() {
        webDriverWrapper.setAlternativeWebDriverConfiguration(NoJsWebDriverConfiguration.class);
    }

}
