package de.telekom.test.frontend.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by d.keiss on 03.04.2017.
 */
@Component
public class BrowserDriverUpdater {

	private Logger log = LoggerFactory.getLogger(WebDriverWrapper.class);

	@Value("${yeti.systest.browser}")
	private String defaultBrowser;

	@Value("${yeti.systest.webdriver.proxy.host}")
	private String proxyHost;

	@Value("${yeti.systest.webdriver.proxy.port}")
	private String proxyPort;

	public void updateDriver() {
		String browser = getBrowser();

		switch (browser) {
		case "firefox": {
			if (StringUtils.isNotBlank(proxyHost) && StringUtils.isNotBlank(proxyPort)) {
				FirefoxDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
			}
			FirefoxDriverManager.getInstance().setup();
			break;
		}
		case "chrome": {
			System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
			if (StringUtils.isNotBlank(proxyHost) && StringUtils.isNotBlank(proxyPort)) {
				ChromeDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
			}
			ChromeDriverManager.getInstance().setup();
			break;
		}
		case "edge": {
			if (StringUtils.isNotBlank(proxyHost) && StringUtils.isNotBlank(proxyPort)) {
				EdgeDriverManager.getInstance().proxy(proxyHost + ":" + proxyPort);
			}
			EdgeDriverManager.getInstance().setup();
			break;
		}
		case "ie": {
			if (StringUtils.isNotBlank(proxyHost) && StringUtils.isNotBlank(proxyPort)) {
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
		if (StringUtils.isBlank(browser)) {
			browser = defaultBrowser;
		}
		browser = browser.toLowerCase();
		return browser;
	}

}
