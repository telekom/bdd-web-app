package de.telekom.test.bddwebapp.frontend.lifecycle;

import io.github.bonigarcia.wdm.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Pushes the WebDriver updates for several browser. Is integrated into the JBehave lifecycle via LifecylceSteps.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrowserDriverUpdater {

    private final Logger log = LoggerFactory.getLogger(WebDriverWrapper.class);

    @Value("${webdriver.proxy.host:#{null}}")
    private String proxyHost;
    @Value("${webdriver.proxy.port:#{null}}")
    private String proxyPort;

    @NonNull
    private final WebDriverWrapper webDriverWrapper;

    /**
     * Here you should be careful that the number of 60 requests per hour in the direction of github is not exceeded.
     * This applies to the driver for firefox and opera.
     *
     * https://github.com/bonigarcia/webdrivermanager
     * https://developer.github.com/v3/#rate-limiting
     */
    public void updateDriver() {
        String browser = webDriverWrapper.getBrowser();
        switch (browser) {
            case "firefox": {
                updateFirefox();
                break;
            }
            case "chrome": {
                updateChrome();
                break;
            }
            case "edge": {
                updateEdge();
                break;
            }
            case "ie": {
                updateInternetExplorer();
                break;
            }
            default: {
                return;
            }
        }

        log.info("Updated driver for " + browser + " test instrumentalization.");
    }

    private void updateFirefox() {
        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).proxy(proxyHost + ":" + proxyPort);
        }
        FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
    }

    private void updateChrome() {
        System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            ChromeDriverManager.getInstance(DriverManagerType.CHROME).proxy(proxyHost + ":" + proxyPort);
        }
        ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
    }

    private void updateEdge() {
        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            EdgeDriverManager.getInstance(DriverManagerType.EDGE).proxy(proxyHost + ":" + proxyPort);
        }
        EdgeDriverManager.getInstance(DriverManagerType.EDGE).setup();
    }

    private void updateInternetExplorer() {
        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            InternetExplorerDriverManager.getInstance(DriverManagerType.IEXPLORER).proxy(proxyHost + ":" + proxyPort);
        }
        InternetExplorerDriverManager.getInstance(DriverManagerType.IEXPLORER).setup();
    }

}
