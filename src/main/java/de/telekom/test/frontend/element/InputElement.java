package de.telekom.test.frontend.element;

/**
 * Created by d.keiss on 05.04.2017.
 */
public class InputElement extends WebElementEnhanced {

	public void setValue(String value) {
		clear();
		sendKeys(value);
	}

}
