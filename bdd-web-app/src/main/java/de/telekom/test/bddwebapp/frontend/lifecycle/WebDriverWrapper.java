package de.telekom.test.bddwebapp.frontend.lifecycle;

import de.telekom.test.bddwebapp.stories.customizing.CurrentStory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Manage the current WebDriver instance.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Igor Cernopolc - Initially added support for RemoteWebDriver
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class WebDriverWrapper {

    public static Class<? extends WebDriverConfiguration> DEFAULT_WEB_DRIVER_CONFIGURATION = UsefulWebDriverConfiguration.class;

    @Autowired
    private List<WebDriverConfiguration> webDriverConfigurations;

    @Autowired
    private CurrentStory currentStory;

    @Getter
    @Setter
    private WebDriver driver;

    public WebDriverConfiguration getCurrentWebDriverConfiguration() {
        return currentStory.getAlternativeWebDriverConfiguration()
                .map(webDriverConfigurationClass -> getAlternativeWebDriverConfiguration(webDriverConfigurationClass))
                .orElse(getDefaultWebDriverConfiguration());
    }

    public WebDriverConfiguration getAlternativeWebDriverConfiguration(Class<? extends WebDriverConfiguration> alternativeWebDriverConfigurationClass) {
        return webDriverConfigurations.stream()
                .filter(webDriverConfiguration -> webDriverConfiguration.getClass().equals(alternativeWebDriverConfigurationClass))
                .findFirst().get();
    }

    public WebDriverConfiguration getDefaultWebDriverConfiguration() {
        return webDriverConfigurations.stream()
                .filter(webDriverConfiguration -> webDriverConfiguration.getClass().equals(DEFAULT_WEB_DRIVER_CONFIGURATION))
                .findFirst().get();
    }

    public void loadWebdriver() {
        WebDriverConfiguration webDriverConfiguration = getCurrentWebDriverConfiguration();
        if (isBlank(webDriverConfiguration.getGridURL())) {
            driver = webDriverConfiguration.loadLocalWebdriver();
        } else {
            driver = webDriverConfiguration.loadRemoteWebdriver();
        }
        webDriverConfiguration.afterLoad(driver);
    }

    public void quit() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (UnreachableBrowserException unreachableBrowserException) {
                log.error(unreachableBrowserException.getMessage());
            }
        }
        driver = null;
    }

    public boolean isClosed() {
        return driver == null;
    }

    public String createScreenshot(String path) {
        try {
            log.info("Create screenshot to '{}'", path);
            if (driver == null) {
                log.error("Can not create screenshot because webdriver is null!");
                return null;
            }
            if (driver instanceof HtmlUnitDriver) {
                log.error("Can not create screenshots for htmlunit!");
                return null;
            }
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(path);
            FileUtils.copyFile(screenshot, destFile);
            return destFile.getAbsolutePath();
        } catch (Exception e) {
            log.error("Exception at capture screenshot", e);
            return null;
        }
    }

}
