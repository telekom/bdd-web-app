package de.telekom.test.frontend.element;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by d.keiss on 05.04.2017.
 */
public class WebElementEnhanced {

	public final static List<String> NOT_INVOKE_WEB_ELEMENT_METHODS = Arrays.asList("setWebElement", "setWebDriver", "exists");

	protected WebElement webElement;
	protected WebDriver webDriver;

	public WebElementEnhanced() {
	}

	public WebElementEnhanced(WebElement webElement) {
		this.webElement = webElement;
	}

	public WebElementEnhanced(WebElement webElement, WebDriver webDriver) {
		this.webElement = webElement;
		this.webDriver = webDriver;
	}

	public void setWebElement(WebElement webElement) {
		this.webElement = webElement;
	}

	public WebElement getWebElement() {
		return webElement;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public void sendKeys(CharSequence... keysToSend) {
		webElement.sendKeys(keysToSend);
	}

	public Point getLocation() {
		return webElement.getLocation();
	}

	public void submit() {
		webElement.submit();
	}

	public String getAttribute(String name) {
		return webElement.getAttribute(name);
	}

	public String getCssValue(String propertyName) {
		return webElement.getCssValue(propertyName);
	}

	public Dimension getSize() {
		return webElement.getSize();
	}

	public Rectangle getRect() {
		return webElement.getRect();
	}

	public List<WebElement> findElements(By by) {
		return webElement.findElements(by);
	}

	public String getText() {
		return webElement.getText();
	}

	public String getTagName() {
		return webElement.getTagName();
	}

	public boolean isSelected() {
		return webElement.isSelected();
	}

	public WebElement findElement(By by) {
		return webElement.findElement(by);
	}

	public boolean isEnabled() {
		return webElement.isEnabled();
	}

	public boolean isDisplayed() {
		return webElement.isDisplayed();
	}

	public void clear() {
		webElement.clear();
	}

	public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
		return webElement.getScreenshotAs(outputType);
	}

	public void waitForDisplayed(int maxWaitTimeInSeconds) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver, maxWaitTimeInSeconds);
		webDriverWait.withMessage("Element: \"" + webElement + "\" is still not displayed!");
		webDriverWait.until(driver -> {
			try {
				return webElement.isDisplayed();
			} catch (WebDriverException e) {
				return false;
			}
		});
	}

	public void scrollTo() {
		if (webDriver == null) {
			throw new IllegalStateException("Webdriver must be set to use this method!");
		}
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webElement);
		waitForDisplayed(1);
	}

	public void click() {
		scrollTo();
		webElement.click();
	}

	public void setValue(String value) {
		clear();
		sendKeys(value);
	}

	public boolean exists() {
		webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		try {
			getWebElement(); // invoke web element
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return false;
		} finally {
			webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
		return true;
	}

	public boolean hasChildren(By by) {
		webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		try {
			webElement.findElement(by);
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return false;
		} finally {
			webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
		return true;
	}

}
