package de.telekom.test.bddwebapp.taxi.customizing.config;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverConfiguration;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Overwrite the default configuration and add a new behaviour after browser load.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Primary /* Overwrite UsefulWebDriverConfiguration */
@Slf4j
public class SpecialWebDriverConfiguration implements WebDriverConfiguration {

    @Autowired
    private WebDriverWrapper webDriverWrapper;

    @Override
    public void afterLoad(WebDriver driver) {
        webDriverWrapper.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriverWrapper.getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        // move browser to left
        webDriverWrapper.getDriver().manage().window().setPosition(new Point(-1500, 0));
    }

}
