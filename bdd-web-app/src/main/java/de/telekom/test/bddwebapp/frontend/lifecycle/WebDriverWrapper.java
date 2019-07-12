package de.telekom.test.bddwebapp.frontend.lifecycle;

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

    @Autowired
    private WebDriverConfiguration webDriverConfiguration;

    @Getter
    @Setter
    private WebDriver driver;

    public void loadWebdriver() {
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
