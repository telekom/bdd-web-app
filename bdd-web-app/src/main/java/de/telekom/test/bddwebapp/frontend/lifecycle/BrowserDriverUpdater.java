package de.telekom.test.bddwebapp.frontend.lifecycle;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Pushes the WebDriver updates for several browser. Is integrated into the JBehave lifecycle via LifecylceSteps.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class BrowserDriverUpdater {

    @Value("${webdriver.proxy.host:#{null}}")
    private String proxyHost;

    @Value("${webdriver.proxy.port:#{null}}")
    private String proxyPort;

    @Autowired
    private WebDriverConfiguration webDriverConfiguration;

    /**
     * Here you should be careful that the number of 60 requests per hour in the direction of github is not exceeded.
     * This applies to the driver for firefox and opera.
     * <p>
     * https://github.com/bonigarcia/webdrivermanager
     * https://developer.github.com/v3/#rate-limiting
     */
    public void updateDriver() {
        String browser = webDriverConfiguration.getBrowser();
        DriverManagerType driverManagerType = mapToDriverManagerType(browser);
        if (driverManagerType == null) {
            log.info("No driver update available for " + browser);
            return;
        }

        WebDriverManager webDriverManager = WebDriverManager.getInstance(driverManagerType);
        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            webDriverManager.proxy(proxyHost + ":" + proxyPort);
        }
        webDriverManager.setup();

        log.info("Updated instrumentalization driver for " + driverManagerType.toString() + "(" + browser + ")");
    }

    private DriverManagerType mapToDriverManagerType(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                return DriverManagerType.FIREFOX;
            case "chrome":
                return DriverManagerType.CHROME;
            case "edge":
                return DriverManagerType.EDGE;
            case "ie":
            case "internetexplorer":
                return DriverManagerType.IEXPLORER;
            case "opera":
                return DriverManagerType.OPERA;
            case "phantomjs":
                return DriverManagerType.PHANTOMJS;
            case "standalone":
            case "seleniumserverstandalone":
                return DriverManagerType.SELENIUM_SERVER_STANDALONE;
        }
        return null;
    }

}
