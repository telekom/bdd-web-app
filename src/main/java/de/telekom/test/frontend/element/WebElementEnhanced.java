package de.telekom.test.frontend.element;

import de.telekom.test.frontend.element.ajax.LazyElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by d.keiss on 05.04.2017.
 */
public class WebElementEnhanced implements WebElement, LazyElement {

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

	@Override
	public void click() {
		webElement.click();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		webElement.sendKeys(keysToSend);
	}

	@Override
	public Point getLocation() {
		return webElement.getLocation();
	}

	@Override
	public void submit() {
		webElement.submit();
	}

	@Override
	public String getAttribute(String name) {
		return webElement.getAttribute(name);
	}

	@Override
	public String getCssValue(String propertyName) {
		return webElement.getCssValue(propertyName);
	}

	@Override
	public Dimension getSize() {
		return webElement.getSize();
	}

	@Override
	public Rectangle getRect() {
		return null;
	}

	@Override
	public List<WebElement> findElements(By by) {
		return webElement.findElements(by);
	}

	@Override
	public String getText() {
		return webElement.getText();
	}

	@Override
	public String getTagName() {
		return webElement.getTagName();
	}

	@Override
	public boolean isSelected() {
		return webElement.isSelected();
	}

	@Override
	public WebElement findElement(By by) {
		return webElement.findElement(by);
	}

	@Override
	public boolean isEnabled() {
		return webElement.isEnabled();
	}

	@Override
	public boolean isDisplayed() {
		return webElement.isDisplayed();
	}

	@Override
	public void clear() {
		webElement.clear();
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
		return webElement.getScreenshotAs(outputType);
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

}
