package de.telekom.test.bddwebapp.frontend.lifecycle;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The configuration for all supported and tested browsers.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface WebDriverConfiguration {

    default WebDriver loadWebdriver() {
        var browser = getBrowser();
        getLogger().info("WebDriver is set to " + browser);
        var extraCapabilities = extraCapabilities(browser);
        if (isRemoteWebdriver()) {
            return loadRemoteWebdriver(extraCapabilities);
        }
        switch (browser.toLowerCase()) {
            case "firefox":
                return loadFirefox(extraCapabilities);
            case "chrome":
                return loadChrome(extraCapabilities);
            case "edge":
                return loadEdge(extraCapabilities);
            case "ie":
            case "internetexplorer":
                return loadInternetExplorer(extraCapabilities);
            case "opera":
                return loadOpera(extraCapabilities);
            case "safari":
                return loadSafari(extraCapabilities);
            case "htmlunit":
                return loadHtmlUnit(extraCapabilities);
            default:
                throw new IllegalArgumentException("No browser defined! Given browser is: " + browser);
        }
    }

    default DesiredCapabilities extraCapabilities(String browser) {
        return new DesiredCapabilities();
    }

    private boolean isRemoteWebdriver() {
        return StringUtils.isNotBlank(getGridURL());
    }

    default DesiredCapabilities remoteWebDriverOptions(DesiredCapabilities capabilities) {
        var remoteWebDriverCapabilities = new DesiredCapabilities();
        remoteWebDriverCapabilities.setJavascriptEnabled(true);
        remoteWebDriverCapabilities.merge(capabilities);
        return remoteWebDriverCapabilities;
    }

    default WebDriver loadRemoteWebdriver(DesiredCapabilities capabilities) {
        var gridURL = getGridURL();
        getLogger().info("Running on: " + gridURL);
        try {
            return new RemoteWebDriver(new URL(gridURL), remoteWebDriverOptions(capabilities));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL for remote webdriver is malformed!", e);
        }
    }

    default FirefoxOptions firefoxOptions(DesiredCapabilities capabilities) {
        var firefoxOptions = new FirefoxOptions();
        if (isHeadless()) {
            getLogger().info("Firefox is set to headless mode");
            firefoxOptions.setHeadless(true);
        }
        firefoxOptions.merge(capabilities);
        return firefoxOptions;
    }

    default WebDriver loadFirefox(DesiredCapabilities capabilities) {
        var firefoxOptions = firefoxOptions(capabilities);
        var browserPath = getBrowserPath();
        if (StringUtils.isNotBlank(browserPath)) {
            getLogger().info("Load portable firefox instance from '{}'", browserPath);
            firefoxOptions.setBinary(browserPath);
        }
        return new FirefoxDriver(firefoxOptions);
    }

    default ChromeOptions chromeOptions(DesiredCapabilities capabilities) {
        var chromeOptions = new ChromeOptions();
        if (isHeadless()) {
            getLogger().info("Chrome is set to headless mode");
            chromeOptions.setHeadless(true);
        }
        chromeOptions.merge(capabilities);
        return chromeOptions;
    }

    default WebDriver loadChrome(DesiredCapabilities capabilities) {
        var chromeOptions = chromeOptions(capabilities);
        var browserPath = getBrowserPath();
        if (StringUtils.isNotBlank(browserPath)) {
            getLogger().info("Load portable chrome instance from '{}'", browserPath);
            chromeOptions.setBinary(browserPath);
        }
        return new ChromeDriver(chromeOptions);
    }

    default EdgeOptions edgeOptions(DesiredCapabilities capabilities) {
        var edgeOptions = new EdgeOptions();
        if (isHeadless()) {
            getLogger().warn("No headless mode for edge available!");
        }
        edgeOptions.merge(capabilities);
        return edgeOptions;
    }

    default WebDriver loadEdge(DesiredCapabilities capabilities) {
        var edgeOptions = edgeOptions(capabilities);
        var browserPath = getBrowserPath();
        if (StringUtils.isNotBlank(browserPath)) {
            throw new IllegalArgumentException("Can't use 'browserPath' for edge browser. Portable is not supported!");
        }
        return new EdgeDriver(edgeOptions);
    }

    default InternetExplorerOptions internetExplorerOptions(DesiredCapabilities capabilities) {
        var internetExplorerOptions = new InternetExplorerOptions();
        if (isHeadless()) {
            getLogger().warn("No headless mode for internet explorer available!");
        }
        internetExplorerOptions.merge(capabilities);
        return internetExplorerOptions;
    }

    default WebDriver loadInternetExplorer(DesiredCapabilities capabilities) {
        var internetExplorerOptions = internetExplorerOptions(capabilities);
        var browserPath = getBrowserPath();
        if (StringUtils.isNotBlank(browserPath)) {
            throw new IllegalArgumentException("Can't use 'browserPath' for Internet Explorer. Portable is not supported!");
        }
        return new InternetExplorerDriver(internetExplorerOptions);
    }

    default OperaOptions operaOptions(DesiredCapabilities capabilities) {
        var operaOptions = new OperaOptions();
        if (isHeadless()) {
            getLogger().warn("No headless mode for Opera available!");
        }
        operaOptions.merge(capabilities);
        return operaOptions;
    }

    default WebDriver loadOpera(DesiredCapabilities capabilities) {
        var operaOptions = operaOptions(capabilities);
        var browserPath = getBrowserPath();
        if (StringUtils.isNotBlank(browserPath)) {
            getLogger().info("Load portable opera instance from '{}'", browserPath);
            operaOptions.setBinary(browserPath);
        }
        return new OperaDriver(operaOptions);
    }

    default SafariOptions safariOptions(DesiredCapabilities capabilities) {
        var safariOptions = new SafariOptions();
        if (isHeadless()) {
            getLogger().warn("No headless mode for Safari available!");
        }
        safariOptions.merge(capabilities);
        return safariOptions;
    }

    default WebDriver loadSafari(DesiredCapabilities capabilities) {
        var safariOptions = safariOptions(capabilities);
        var browserPath = getBrowserPath();
        if (StringUtils.isNotBlank(browserPath)) {
            throw new IllegalArgumentException("Can't use 'browserPath' for Safari. Portable is not supported!");
        }
        return new SafariDriver(safariOptions);
    }

    default DesiredCapabilities htmlUnitOptions(DesiredCapabilities capabilities) {
        var htmlUnitCapabilities = new DesiredCapabilities();
        htmlUnitCapabilities.setBrowserName("htmlunit");
        htmlUnitCapabilities.setJavascriptEnabled(true);
        htmlUnitCapabilities.merge(capabilities);
        return htmlUnitCapabilities;
    }

    default WebDriver loadHtmlUnit(DesiredCapabilities capabilities) {
        return new HtmlUnitDriver(htmlUnitOptions(capabilities));
    }

    default void afterLoad(WebDriver driver) {
    }

    default String getBrowser() {
        var browser = System.getProperty("browser");
        if (StringUtils.isNotBlank(browser)) {
            return browser;
        }
        if (isHeadless()) {
            return "htmlunit";
        } else {
            return "chrome";
        }
    }

    default boolean isHeadless() {
        // set to headless manually
        if (StringUtils.isNotBlank(System.getProperty("headless"))) {
            var headless = Boolean.parseBoolean(System.getProperty("headless"));
            getLogger().info(String.format("Test execution is set to headless=%s!", headless));
            return headless;
        }
        // headless detection
        if (GraphicsEnvironment.isHeadless()) {
            getLogger().info("Headless execution detected! You can override this with \"headless=false\".");
            return true;
        } else {
            getLogger().info("Execution with graphical user interface detected! You can override this with \"headless=true\".");
            return false;
        }
    }

    /**
     * Path for portable browser
     *
     * @return path
     */
    default String getBrowserPath() {
        var browserPath = System.getProperty("browser.path");
        if (StringUtils.isNotBlank(browserPath)) {
            return browserPath;
        }
        return null;
    }

    default String getGridURL() {
        var urlStr = System.getProperty("gridURL");
        if (StringUtils.isNotBlank(urlStr)) {
            return urlStr;
        }
        return null;
    }

    private Logger getLogger() {
        return LoggerFactory.getLogger(WebDriverConfiguration.class);
    }

}