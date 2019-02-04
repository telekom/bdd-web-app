package de.telekom.test.bddwebapp.frontend.lifecycle;

import io.github.bonigarcia.wdm.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BrowserDriverUpdater {

    @Value("${webdriver.proxy.host:#{null}}")
    private String proxyHost;
    @Value("${webdriver.proxy.port:#{null}}")
    private String proxyPort;

    @NonNull
    private final WebDriverWrapper webDriverWrapper;

    /**
     * Here you should be careful that the number of 60 requests per hour in the direction of github is not exceeded.
     * This applies to the driver for firefox and opera.
     * <p>
     * https://github.com/bonigarcia/webdrivermanager
     * https://developer.github.com/v3/#rate-limiting
     */
    public void updateDriver() {
        String browser = webDriverWrapper.getBrowser();

        WebDriverManager webDriverManager;
        switch (browser.toLowerCase()) {
            case "firefox": {
                webDriverManager = FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX);
                break;
            }
            case "chrome": {
                System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
                webDriverManager = ChromeDriverManager.getInstance(DriverManagerType.CHROME);
                break;
            }
            case "edge": {
                webDriverManager = EdgeDriverManager.getInstance(DriverManagerType.EDGE);
                break;
            }
            case "ie": {
                webDriverManager = InternetExplorerDriverManager.getInstance(DriverManagerType.IEXPLORER);
                break;
            }
            case "opera": {
                webDriverManager = OperaDriverManager.getInstance(DriverManagerType.OPERA);
                break;
            }
            case "phantomjs": {
                webDriverManager = PhantomJsDriverManager.getInstance(DriverManagerType.PHANTOMJS);
                break;
            }
            case "seleniumserverstandalone": {
                webDriverManager = SeleniumServerStandaloneManager.getInstance(DriverManagerType.SELENIUM_SERVER_STANDALONE);
                break;
            }
            default: {
                return;
            }
        }

        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            webDriverManager.proxy(proxyHost + ":" + proxyPort);
        }
        webDriverManager.setup();

        log.info("Updated driver for " + browser + " test instrumentalization.");
    }

}
