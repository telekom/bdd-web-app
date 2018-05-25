package de.telekom.jbehave.webapp.frontend.lifecycle;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
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
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrowserDriverUpdater {

    private final Logger log = LoggerFactory.getLogger(WebDriverWrapper.class);

    private final @NonNull
    WebDriverWrapper webDriverWrapper;

    @Value("${webdriver.proxy.host:#{null}}")
    private String proxyHost;

    @Value("${webdriver.proxy.port:#{null}}")
    private String proxyPort;

    /*
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
            FirefoxDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
        }
        FirefoxDriverManager.getInstance().setup();
    }

    private void updateChrome() {
        System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            ChromeDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
        }
        ChromeDriverManager.getInstance().setup();
    }

    private void updateEdge() {
        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            EdgeDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
        }
        EdgeDriverManager.getInstance().setup();
    }

    private void updateInternetExplorer() {
        if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
            InternetExplorerDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
        }
        InternetExplorerDriverManager.getInstance().setup();
    }

}
