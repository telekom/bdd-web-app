package de.telekom.test.bddwebapp.taxi.config;

import de.telekom.test.bddwebapp.frontend.lifecycle.UsefulWebDriverConfiguration;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.BrowserWebDriverContainer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

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
public class TestdriverWebDriverConfiguration extends UsefulWebDriverConfiguration {

    private final BrowserWebDriverContainer container = new BrowserWebDriverContainer()
            .withCapabilities(new ChromeOptions())
            .withRecordingMode(RECORD_ALL, new File("build"));

    @PostConstruct
    public void overrideDefaultWebDriverConfiguration() {
        WebDriverWrapper.DEFAULT_WEB_DRIVER_CONFIGURATION = TestdriverWebDriverConfiguration.class;
    }

    public WebDriver loadWebdriver() {
        return container.getWebDriver();
    }

    public BrowserWebDriverContainer getContainer() {
        return container;
    }
}
