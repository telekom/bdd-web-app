package de.telekom.test.bddwebapp.taxi.seleniumgrid.config.webdriver;

import de.telekom.test.bddwebapp.frontend.lifecycle.UsefulWebDriverConfiguration;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Overwrite the default configuration and add a new behaviour after browser load.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class OverrideDefaultWebDriverConfiguration extends UsefulWebDriverConfiguration {

    public OverrideDefaultWebDriverConfiguration() {
        WebDriverWrapper.DEFAULT_WEB_DRIVER_CONFIGURATION = OverrideDefaultWebDriverConfiguration.class;
    }

    @Override
    public DesiredCapabilities remoteWebDriverOptions(DesiredCapabilities capabilities) {
        DesiredCapabilities remoteWebDriverCapabilities = new DesiredCapabilities();
        remoteWebDriverCapabilities.merge(capabilities);
        remoteWebDriverCapabilities.setPlatform(Platform.ANY);
        remoteWebDriverCapabilities.setBrowserName("chrome");
        remoteWebDriverCapabilities.setCapability("disable-restore-session-state", true);
        remoteWebDriverCapabilities.setCapability("disable-application-cache", true);
        remoteWebDriverCapabilities.setCapability("useAutomationExtension", false);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        remoteWebDriverCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        return remoteWebDriverCapabilities;
    }

    @Override
    public WebDriver loadRemoteWebdriver(DesiredCapabilities capabilities) {
        String gridURL = getGridURL();
        log.info("Running on: " + gridURL);
        try {
            return new RemoteWebDriver(new URL(gridURL), remoteWebDriverOptions(capabilities));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL for remote webdriver is malformed!", e);
        }
    }

}
