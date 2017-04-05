package de.telekom.test.frontend.element;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by d.keiss on 05.04.2017.
 */
public class InputElement extends WebElementEnhanced {

	public InputElement() {
	}

	public InputElement(WebElement webElement, WebDriver webDriver) {
		this.webElement = webElement;
		this.webDriver = webDriver;
	}

	public void setValue(String value) {
		clear();
		sendKeys(value);
	}

}
