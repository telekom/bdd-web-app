package de.telekom.test.frontend.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base class for page objects. Page objects represent a certain page of a certain YETI module. It models test relevant interactions and state as an easy to use
 * object.
 * <p>
 * Page objects should provide methods for interactions that return subsequent pages as page objects as well.
 */
public abstract class Page {

	protected final WebDriver driver;

	protected Page(WebDriver driver) {
		this.driver = driver;
		checkUrl();
	}

	public void checkUrl() {
		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		wait.until(new UrlMatchesExpectation(driver, getURL(), this.getClass().getName()));
	}

	public void reload() {
		driver.navigate().refresh();
	}

	public abstract String getURL();

}
