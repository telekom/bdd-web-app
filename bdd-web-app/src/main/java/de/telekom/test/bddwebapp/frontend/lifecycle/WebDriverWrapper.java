package de.telekom.test.bddwebapp.frontend.lifecycle;

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

import static java.util.Optional.ofNullable;

/**
 * Manage the current WebDriver instance.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Igor Cernopolc - Initially added support for RemoteWebDriver
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class WebDriverWrapper {

    public static Class<? extends WebDriverConfiguration> DEFAULT_WEB_DRIVER_CONFIGURATION = UsefulWebDriverConfiguration.class;

    @Autowired
    private List<WebDriverConfiguration> webDriverConfigurations;

    private final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private final ThreadLocal<Class<? extends WebDriverConfiguration>> alternativeWebDriverConfiguration = new ThreadLocal<>();

    public WebDriver getDriver() {
        WebDriver webDriver = this.webDriver.get();
        if (webDriver == null) {
            loadWebdriver();
        }
        return this.webDriver.get();
    }

    public void setDriver(WebDriver webDriver) {
        this.webDriver.set(webDriver);
    }

    public Class<? extends WebDriverConfiguration> getAlternativeWebDriverConfiguration() {
        return this.alternativeWebDriverConfiguration.get();
    }

    public void setAlternativeWebDriverConfiguration(Class<? extends WebDriverConfiguration> alternativeWebDriverConfiguration) {
        this.alternativeWebDriverConfiguration.set(alternativeWebDriverConfiguration);
    }

    public void resetAlternativeWebDriverConfiguration() {
        this.alternativeWebDriverConfiguration.set(null);
    }

    public WebDriverConfiguration getCurrentWebDriverConfiguration() {
        return ofNullable(alternativeWebDriverConfiguration.get())
                .map(this::getAlternativeWebDriverConfiguration)
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
        setDriver(webDriverConfiguration.loadWebdriver());
        webDriverConfiguration.afterLoad(getDriver());
    }

    public void quit() {
        if (getDriver() != null) {
            try {
                getDriver().quit();
            } catch (UnreachableBrowserException unreachableBrowserException) {
                log.error(unreachableBrowserException.getMessage());
            }
        }
        setDriver(null);
    }

    public boolean isClosed() {
        return getDriver() == null;
    }

    public String createScreenshot(String path) {
        try {
            log.info("Create screenshot to '{}'", path);
            if (getDriver() == null) {
                log.error("Can not create screenshot because webdriver is null!");
                return null;
            }
            if (getDriver() instanceof HtmlUnitDriver) {
                log.error("Can not create screenshots for htmlunit!");
                return null;
            }
            File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            File destFile = new File(path);
            FileUtils.copyFile(screenshot, destFile);
            return destFile.getAbsolutePath();
        } catch (Exception e) {
            log.error("Exception at capture screenshot", e);
            return null;
        }
    }

}
