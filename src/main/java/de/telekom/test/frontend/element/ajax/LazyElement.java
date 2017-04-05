package de.telekom.test.frontend.element.ajax;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public interface LazyElement {

	default void waitUntil(WaitFor waitFor, String errorMessage, int maxWaitTimeInSeconds) {
		int attemptWaitTime = 250;
		int attempts = maxWaitTimeInSeconds * 1000 / attemptWaitTime; // try every 250ms

		WebDriverException latestException = null;

		for (int i = 0; i < attempts; i++) {
			try {
				if (waitFor.isFinished()) {
					return;
				}
			} catch (WebDriverException wde) {
				latestException = wde;
			}
			try {
				synchronized (this) {
					wait(attemptWaitTime);
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		throw new RuntimeException(errorMessage, latestException);
	}

	default void waitForDisplayed(int maxWaitTimeInSeconds) {
		String errorMessage = "Element: \"" + getWebElement() + "\" is still not displayed!";
		waitUntil(getWebElement()::isDisplayed, errorMessage, maxWaitTimeInSeconds);
	}

	WebElement getWebElement();

}
