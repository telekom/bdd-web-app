package de.telekom.test.frontend.pages;

import de.telekom.test.frontend.element.WebElementEnhanced;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Base class for page objects. Page objects represent a certain page of a certain YETI module. It models test relevant interactions and state as an easy to use
 * object.
 * <p>
 * Page objects should provide methods for interactions that return subsequent pages as page objects as well.
 */
public abstract class Page {

	protected WebDriver driver;

	protected Page(WebDriver driver) {
		this.driver = driver;
		checkUrl(driver);
	}

	private void checkUrl(WebDriver driver) {
		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		wait.until(new UrlMatchesExpectation(driver, getURL(), this.getClass().getName()));
	}

	public void reload() {
		driver.navigate().refresh();
	}

	public boolean elementExists(WebElement element) {
		if (element instanceof WebElementEnhanced) {
			element = ((WebElementEnhanced) element).getWebElement();
		}
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		try {
			element.getLocation();
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
		return true;
	}

	protected abstract String getURL();

}
