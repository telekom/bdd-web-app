package de.telekom.test.frontend.element;

import de.telekom.test.frontend.element.ajax.LazyElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by d.keiss on 05.04.2017.
 */
public class WebElementEnhanced implements LazyElement {

	protected WebElement webElement;
	protected WebDriver webDriver;

	public WebElementEnhanced() {
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

	public void click() {
		webElement.click();
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
		return null;
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
		String errorMessage = "Element: \"" + getWebElement() + "\" is still not displayed!";
		waitUntil(webElement::isDisplayed, errorMessage, maxWaitTimeInSeconds);
	}

	public void scrollTo() {
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webElement);
		Wait<WebDriver> wait = new WebDriverWait(webDriver, 1);
		wait.until(driver -> {
			try {
				return webElement.isDisplayed();
			} catch (WebDriverException e) {
				return true;
			}
		});
	}

	public void setValue(String value) {
		clear();
		sendKeys(value);
	}

}
