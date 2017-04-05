package de.telekom.test.frontend.lifecycle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Create and manage WebDriver Instance for specific browser.
 *
 * @author d.keiss
 * @version $Id: WebDriverWrapper.java 9695 2014-01-10 12:57:07Z d.keiss $
 */
@Component
public class WebDriverWrapper implements WebDriverProvider {

	private Logger log = LoggerFactory.getLogger(WebDriverWrapper.class);

	@Value("${yeti.systest.browser}")
	private String defaultBrowser;

	@Value("${yeti.systest.webdriver.proxy.host}")
	private String proxyHost;

	@Value("${yeti.systest.webdriver.proxy.port}")
	private String proxyPort;

	private WebDriver driver;

	public void loadWebdriver() {
		String browser = getBrowser();

		log.info("Brower set to: " + browser);

		switch (browser) {
		case "firefox": {
			initFirefox();
			break;
		}
		case "chrome": {
			initChrome();
			break;
		}
		case "edge": {
			initEdge();
			break;
		}
		case "ie": {
			initInternetExplorer();
			break;
		}
		case "safari": {
			initSafari();
			break;
		}
		default:
			throw new IllegalArgumentException("No browser defined!");
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	private String getBrowser() {
		String browser = System.getProperty("browser");
		if (StringUtils.isBlank(browser)) {
			browser = defaultBrowser;
		}
		browser = browser.toLowerCase();
		return browser;
	}

	private void initFirefox() {
		DesiredCapabilities caps = DesiredCapabilities.firefox();
		caps.setCapability("overlappingCheckDisabled", true);

		driver = new FirefoxDriver(caps);
	}

	private void initChrome() {
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("disable-restore-session-state", true);
		caps.setCapability("disable-application-cache", true);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("--start-maximized");
		caps.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new ChromeDriver(caps);
	}

	private void initSafari() {
		driver = new SafariDriver();
	}

	private void initInternetExplorer() {
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieCapabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
		ieCapabilities.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, true);
		ieCapabilities.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
		ieCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		ieCapabilities.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, true);
		ieCapabilities.setCapability(CapabilityType.SUPPORTS_SQL_DATABASE, true);
		ieCapabilities.setCapability(CapabilityType.SUPPORTS_WEB_STORAGE, true);
		ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		driver = new InternetExplorerDriver(ieCapabilities);
	}

	private void initEdge() {
		driver = new EdgeDriver();
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

	@Override
	public WebDriver get() {
		return driver;
	}

	@Override
	public void initialize() {
		loadWebdriver();
	}

	@Override
	public boolean saveScreenshotTo(String path) {
		try {
			log.info("Create screenshot to '{}'", path);
			if (driver == null) {
				log.error("Can not create screenshot because webdriver is null!");
				return false;
			}
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path));
			return true;
		} catch (Exception e) {
			log.error("Exception at capture screenshot", e);
			return false;
		}
	}

	@Override
	public void end() {
		quit();
	}
}
