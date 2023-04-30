package de.telekom.test.bddwebapp.frontend.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * A useful configuration with some additions to selenium default for all supported and tested browsers.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class UsefulWebDriverConfiguration implements WebDriverConfiguration {

    @Value("${webdriver.maximize:false}")
    private boolean maximizeBrowser;

    @Override
    public FirefoxOptions firefoxOptions(DesiredCapabilities capabilities) {
        var firefoxOptions = new FirefoxOptions();
        if (isHeadless()) {
            log.info("Firefox is set to headless mode");
            firefoxOptions.addArguments("-headless");
        }
        capabilities.setCapability("overlappingCheckDisabled", true);
        firefoxOptions.merge(capabilities);
        return firefoxOptions;
    }

    @Override
    public ChromeOptions chromeOptions(DesiredCapabilities capabilities) {
        var chromeOptions = new ChromeOptions();
        if (isHeadless()) {
            log.info("Chrome is set to headless mode");
            chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--headless=new");
        }
        chromeOptions.addArguments("--remote-allow-origins=*");
        capabilities.setCapability("disable-restore-session-state", true);
        capabilities.setCapability("disable-application-cache", true);
        capabilities.setCapability("useAutomationExtension", false);
        chromeOptions.merge(capabilities);
        return chromeOptions;
    }

    @Override
    public void afterLoad(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        if (maximizeBrowser) {
            driver.manage().window().maximize();
        }
    }

}
