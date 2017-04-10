package de.telekom.test.frontend.lifecycle;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by d.keiss on 03.04.2017.
 */
@Component
public class BrowserDriverUpdater {

	private final Logger log = LoggerFactory.getLogger(WebDriverWrapper.class);

	@Value("${default.browser:@null}")
	private String defaultBrowser;

	@Value("${webdriver.proxy.host:@null}")
	private String proxyHost;

	@Value("${webdriver.proxy.port:@null}")
	private String proxyPort;

	/*
	 * Here you should be careful that the number of 60 requests per hour in the direction of github is not exceeded.
	 * This applies to the driver for firefox and opera.
	 *
	 * https://github.com/bonigarcia/webdrivermanager
	 * https://developer.github.com/v3/#rate-limiting
	 */
	public void updateDriver() {
		String browser = getBrowser();

		switch (browser) {
		case "firefox": {
			if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
				FirefoxDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
			}
			FirefoxDriverManager.getInstance().setup();
			break;
		}
		case "chrome": {
			System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
			if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
				ChromeDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
			}
			ChromeDriverManager.getInstance().setup();
			break;
		}
		case "edge": {
			if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
				EdgeDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
			}
			EdgeDriverManager.getInstance().setup();
			break;
		}
		case "ie": {
			if (isNotBlank(proxyHost) && isNotBlank(proxyPort)) {
				InternetExplorerDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
			}
			InternetExplorerDriverManager.getInstance().setup();
			break;
		}
		default: {
			return;
		}
		}

		log.info("Updated instrumentalisation driver for browser: " + browser);
	}

	private String getBrowser() {
		String browser = System.getProperty("browser");
		if (isBlank(browser) && isNotBlank(defaultBrowser)) {
			browser = defaultBrowser;
		} else {
			browser = "chrome";
		}
		browser = browser.toLowerCase();
		return browser;
	}

}
