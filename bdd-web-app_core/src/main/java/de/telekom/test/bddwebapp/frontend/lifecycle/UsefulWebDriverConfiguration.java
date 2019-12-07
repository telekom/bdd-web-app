package de.telekom.test.bddwebapp.frontend.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * A useful configuration with some additions to selenium default for all supported and tested browsers.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class UsefulWebDriverConfiguration implements WebDriverConfiguration {

    @Value("${webdriver.maximize:false}")
    private boolean maximizeBrowser;

    @Override
    public WebDriver loadChrome(DesiredCapabilities capabilities) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.merge(capabilities);
        String browserPath = getBrowserPath();
        if (isNotBlank(browserPath)) {
            LoggerFactory.getLogger(WebDriverConfiguration.class).info("Load portable chrome instance from '{}'", browserPath);
            chromeOptions.setBinary(browserPath);
        }
        return new ChromeDriver(chromeOptions);
    }

    @Override
    public FirefoxOptions firefoxOptions(DesiredCapabilities capabilities) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        capabilities.setCapability("overlappingCheckDisabled", true);
        firefoxOptions.merge(capabilities);
        return firefoxOptions;
    }

    @Override
    public ChromeOptions chromeOptions(DesiredCapabilities capabilities) {
        ChromeOptions chromeOptions = new ChromeOptions();
        capabilities.setCapability("disable-restore-session-state", true);
        capabilities.setCapability("disable-application-cache", true);
        capabilities.setCapability("useAutomationExtension", false);
        chromeOptions.merge(capabilities);
        return chromeOptions;
    }

    @Override
    public void afterLoad(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        if (maximizeBrowser) {
            driver.manage().window().maximize();
        }
    }

}
