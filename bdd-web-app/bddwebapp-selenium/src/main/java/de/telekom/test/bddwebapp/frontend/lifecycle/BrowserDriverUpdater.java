package de.telekom.test.bddwebapp.frontend.lifecycle;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Pushes the WebDriver updates for several browser. Is integrated into the JBehave lifecycle via WebDriverLifecycleSteps.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
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

    private final WebDriverWrapper webDriverWrapper;

    public BrowserDriverUpdater(WebDriverWrapper webDriverWrapper) {
        this.webDriverWrapper = webDriverWrapper;
    }

    /**
     * Here you should be careful that the number of 60 requests per hour in the direction of github is not exceeded.
     * This applies to the driver for firefox and opera.
     * <p>
     * <a href="https://github.com/bonigarcia/webdrivermanager">...</a>
     * <a href="https://developer.github.com/v3/#rate-limiting">...</a>
     */
    public void updateDriver() {
        var currentWebDriverConfiguration = webDriverWrapper.getCurrentWebDriverConfiguration();
        var browser = currentWebDriverConfiguration.getBrowser();
        var driverManagerType = mapToDriverManagerType(browser);
        if (driverManagerType == null) {
            log.info("No driver update available for " + browser + " browser");
            return;
        }
        log.info("Update driver for " + browser);

        WebDriverManager.getInstance(driverManagerType).setup();
        log.info("Updated instrumentalization driver for " + driverManagerType + "(" + browser + ")");
    }

    private DriverManagerType mapToDriverManagerType(String browser) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> DriverManagerType.FIREFOX;
            case "chrome" -> DriverManagerType.CHROME;
            case "edge" -> DriverManagerType.EDGE;
            case "ie", "internetexplorer" -> DriverManagerType.IEXPLORER;
            case "opera" -> DriverManagerType.OPERA;
            default -> null;
        };
    }

}
