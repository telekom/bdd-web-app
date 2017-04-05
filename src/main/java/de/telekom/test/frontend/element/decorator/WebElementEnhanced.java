package de.telekom.test.frontend.element.decorator;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by d.keiss on 05.04.2017.
 */
public class WebElementEnhanced implements WebElement {

	protected WebElement element;
	protected WebDriver webDriver;

	public void setElement(WebElement element) {
		this.element = element;
	}

	public WebElement getElement() {
		return element;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	@Override
	public void click() {
		element.click();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		element.sendKeys(keysToSend);
	}

	@Override
	public Point getLocation() {
		return element.getLocation();
	}

	@Override
	public void submit() {
		element.submit();
	}

	@Override
	public String getAttribute(String name) {
		return element.getAttribute(name);
	}

	@Override
	public String getCssValue(String propertyName) {
		return element.getCssValue(propertyName);
	}

	@Override
	public Dimension getSize() {
		return element.getSize();
	}

	@Override
	public Rectangle getRect() {
		return null;
	}

	@Override
	public List<WebElement> findElements(By by) {
		return element.findElements(by);
	}

	@Override
	public String getText() {
		return element.getText();
	}

	@Override
	public String getTagName() {
		return element.getTagName();
	}

	@Override
	public boolean isSelected() {
		return element.isSelected();
	}

	@Override
	public WebElement findElement(By by) {
		return element.findElement(by);
	}

	@Override
	public boolean isEnabled() {
		return element.isEnabled();
	}

	@Override
	public boolean isDisplayed() {
		return element.isDisplayed();
	}

	@Override
	public void clear() {
		element.clear();
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
		return element.getScreenshotAs(outputType);
	}

	public void scrollTo() {
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", element);
		Wait<WebDriver> wait = new WebDriverWait(webDriver, 1);
		wait.until(driver -> {
			try {
				return element.isDisplayed();
			} catch (WebDriverException e) {
				return true;
			}
		});
	}

}
