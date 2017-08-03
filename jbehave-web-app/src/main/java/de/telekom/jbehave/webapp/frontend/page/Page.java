package de.telekom.jbehave.webapp.frontend.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Abstract base class for page objects. Checks the current url when creating.
 *
 * @author Daniel Keiss
 */
public abstract class Page {

	protected final WebDriver driver;

	public Page(WebDriver driver) {
		this.driver = driver;
		checkUrl();
	}

	public void checkUrl() {
		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		wait.until(new UrlMatchesExpectation(getURL(), this.getClass().getName()));
	}

	public void reload() {
		driver.navigate().refresh();
	}

	public abstract String getURL();

}
