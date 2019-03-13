package de.telekom.test.bddwebapp.frontend.lifecycle;

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
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * The configuration for all supported and tested browsers.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface WebDriverConfiguration {

    default DesiredCapabilities capabilities(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox": {
                return firefoxCapabilities();
            }
            case "chrome": {
                return chromeCapabilities();
            }
            case "edge": {
                return edgeCapabilities();
            }
            case "ie": {
                return ieCapabilities();
            }
            case "opera": {
                return operaCapabilities();
            }
            case "safari": {
                return safariCapabilities();
            }
            case "htmlunit": {
                return htmlUnitCapabilities();
            }
            default: {
                throw new IllegalArgumentException("No browser defined! Given browser is: " + browser);
            }
        }
    }

    default DesiredCapabilities firefoxCapabilities() {
        return DesiredCapabilities.firefox();
    }

    default DesiredCapabilities chromeCapabilities() {
        return DesiredCapabilities.chrome();
    }

    default DesiredCapabilities edgeCapabilities() {
        return DesiredCapabilities.edge();
    }

    default DesiredCapabilities ieCapabilities() {
        return DesiredCapabilities.internetExplorer();
    }

    default DesiredCapabilities operaCapabilities() {
        return DesiredCapabilities.operaBlink();
    }

    default DesiredCapabilities safariCapabilities() {
        return DesiredCapabilities.safari();
    }

    default DesiredCapabilities htmlUnitCapabilities() {
        return DesiredCapabilities.htmlUnit();
    }

    default WebDriver loadLocalWebdriver() {
        String browser = getBrowser();
        LoggerFactory.getLogger(WebDriverConfiguration.class).info("Browser is set to: " + browser);
        DesiredCapabilities capabilities = capabilities(browser);
        switch (browser.toLowerCase()) {
            case "firefox": {
                return loadFirefox(capabilities);
            }
            case "chrome": {
                return loadChrome(capabilities);
            }
            case "edge": {
                return loadEdge(capabilities);
            }
            case "ie": {
                return loadInternetExplorer(capabilities);
            }
            case "opera": {
                return loadOpera(capabilities);
            }
            case "safari": {
                return loadSafari(capabilities);
            }
            case "htmlunit": {
                return loadHtmlUnit(capabilities);
            }
            default:
                throw new IllegalArgumentException("No browser defined! Given browser is: " + browser);
        }
    }

    default WebDriver loadRemoteWebdriver() {
        String gridURL = getGridURL();
        LoggerFactory.getLogger(WebDriverConfiguration.class).info("Running on: " + gridURL);
        DesiredCapabilities capabilities = capabilities(getBrowser());
        try {
            return new RemoteWebDriver(new URL(gridURL), capabilities);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL for remote webdriver is malformed!", e);
        }
    }

    default WebDriver loadFirefox(DesiredCapabilities capabilities) {
        FirefoxOptions firefoxOptions = new FirefoxOptions(capabilities);
        String browserPath = getBrowserPath();
        if (isNotBlank(browserPath)) {
            LoggerFactory.getLogger(WebDriverConfiguration.class).info("Load portable firefox instance from '{}'", browserPath);
            firefoxOptions.setBinary(browserPath);
        }
        return new FirefoxDriver(firefoxOptions);
    }

    default WebDriver loadChrome(DesiredCapabilities capabilities) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(capabilities);
        String browserPath = getBrowserPath();
        if (isNotBlank(browserPath)) {
            LoggerFactory.getLogger(WebDriverConfiguration.class).info("Load portable chrome instance from '{}'", browserPath);
            chromeOptions.setBinary(browserPath);
        }
        return new ChromeDriver(chromeOptions);
    }

    default WebDriver loadEdge(DesiredCapabilities capabilities) {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.merge(capabilities);
        String browserPath = getBrowserPath();
        if (isNotBlank(browserPath)) {
            throw new IllegalArgumentException("Can't use 'browserPath' for edge browser. Portable is not supported!");
        }
        return new EdgeDriver(edgeOptions);
    }

    default WebDriver loadInternetExplorer(DesiredCapabilities capabilities) {
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions(capabilities);
        String browserPath = getBrowserPath();
        if (isNotBlank(browserPath)) {
            throw new IllegalArgumentException("Can't use 'browserPath' for Internet Explorer. Portable is not supported!");
        }
        return new InternetExplorerDriver(internetExplorerOptions);
    }

    default WebDriver loadOpera(DesiredCapabilities capabilities) {
        OperaOptions operaOptions = new OperaOptions();
        operaOptions.merge(capabilities);
        String browserPath = getBrowserPath();
        if (isNotBlank(browserPath)) {
            LoggerFactory.getLogger(WebDriverConfiguration.class).info("Load portable opera instance from '{}'", browserPath);
            operaOptions.setBinary(browserPath);
        }
        return new OperaDriver(operaOptions);
    }

    default WebDriver loadSafari(DesiredCapabilities capabilities) {
        SafariOptions safariOptions = new SafariOptions();
        safariOptions.merge(capabilities);
        String browserPath = getBrowserPath();
        if (isNotBlank(browserPath)) {
            throw new IllegalArgumentException("Can't use 'browserPath' for Safari. Portable is not supported!");
        }
        return new SafariDriver(safariOptions);
    }

    default WebDriver loadHtmlUnit(DesiredCapabilities capabilities) {
        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(capabilities);
        htmlUnitDriver.setJavascriptEnabled(true);
        return htmlUnitDriver;
    }

    default void afterLoad(WebDriver driver) {
    }

    default String getBrowser() {
        String browser = System.getProperty("browser");
        if (isNotBlank(browser)) {
            return browser;
        }
        return "chrome";
    }

    /**
     * Path for portable browser
     */
    default String getBrowserPath() {
        String browserPath = System.getProperty("browser.path");
        if (isNotBlank(browserPath)) {
            return browserPath;
        }
        return null;
    }

    default String getGridURL() {
        String urlStr = System.getProperty("gridURL");
        if (isNotBlank(urlStr)) {
            return urlStr;
        }
        return null;
    }

}
