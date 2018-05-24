package de.telekom.jbehave.webapp.frontend.lifecycle;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Manage the current WebDriver instance. Uses proven standard configurations for specific browsers. The lifecycle is determined by SeleniumTestRule.
 *
 * @author Daniel Keiss
 * @author Igor Cernopolc - Initially added support for RemoteWebDriver
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 */
@Component
public class WebDriverWrapper {

    private final Logger log = LoggerFactory.getLogger(WebDriverWrapper.class);

    @Value("${default.browser:#{null}}")
    private String defaultBrowser;

    @Value("${webdriver.proxy.host:#{null}}")
    private String proxyHost;

    @Value("${webdriver.proxy.port:#{null}}")
    private String proxyPort;

    private WebDriver driver;

    public void loadWebdriver() {
        String browser = getBrowser();
        String gridURL = getGridURL();
        DesiredCapabilities capabilities = getCapabilities(browser);

        if (isBlank(gridURL)) {
            loadLocalWebdriver(browser, capabilities);
        } else {
            loadRemoteWebdriver(gridURL, capabilities);
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }

    private void loadLocalWebdriver(String browser, DesiredCapabilities capabilities) {
        log.info("Browser is set to: " + browser);
        switch (browser) {
            case "firefox": {
                FirefoxOptions firefoxOptions = new FirefoxOptions(capabilities);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            }
            case "chrome": {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.merge(capabilities);
                driver = new ChromeDriver(chromeOptions);
                break;
            }
            case "edge": {
                driver = new EdgeDriver();
                break;
            }
            case "ie": {
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions(capabilities);
                driver = new InternetExplorerDriver(internetExplorerOptions);
                break;
            }
            case "safari": {
                driver = new SafariDriver();
                break;
            }
            default:
                throw new IllegalArgumentException("No browser defined! Given browser is: " + browser);
        }
    }

    private void loadRemoteWebdriver(String gridURL, DesiredCapabilities capabilities) {
        log.info("Running on: " + gridURL);
        try {
            driver = new RemoteWebDriver(new URL(gridURL), capabilities);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL for remote webdriver is malformed!", e);
        }
    }

    private DesiredCapabilities getCapabilities(String browser) {
        DesiredCapabilities caps = null;

        switch (browser) {
            case "firefox": {
                caps = DesiredCapabilities.firefox();
                caps.setCapability("overlappingCheckDisabled", true);
                break;
            }
            case "chrome": {
                caps = DesiredCapabilities.chrome();
                caps.setCapability("disable-restore-session-state", true);
                caps.setCapability("disable-application-cache", true);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-extensions");
                caps.setCapability(ChromeOptions.CAPABILITY, options);
                break;
            }
            case "ie": {
                caps = DesiredCapabilities.internetExplorer();
                caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                caps.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
                caps.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, true);
                caps.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
                caps.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
                caps.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, true);
                caps.setCapability(CapabilityType.SUPPORTS_SQL_DATABASE, true);
                caps.setCapability(CapabilityType.SUPPORTS_WEB_STORAGE, true);
                caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                break;
            }
            case "edge":
            case "safari": {
                break;
            }
            default: {
                throw new IllegalArgumentException("No browser defined! Given browser is: " + browser);
            }
        }
        return caps;
    }

    public String getBrowser() {
        String browser = System.getProperty("browser");
        if (isBlank(browser)) {
            if (isNotBlank(defaultBrowser)) {
                browser = defaultBrowser;
            } else {
                browser = "chrome";
            }
        }
        browser = browser.toLowerCase();
        return browser;
    }

    private String getGridURL() {
        String urlStr = System.getProperty("gridURL");
        if (isNotBlank(urlStr)) {
            return urlStr;
        }
        return null;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
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

    public String saveScreenshotTo(String path) {
        try {
            log.info("Create screenshot to '{}'", path);
            if (driver == null) {
                log.error("Can not create screenshot because webdriver is null!");
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
