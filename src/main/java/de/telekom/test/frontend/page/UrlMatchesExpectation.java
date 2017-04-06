package de.telekom.test.frontend.page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class UrlMatchesExpectation implements ExpectedCondition<Boolean> {

	private final String detail;
	private String urlToMatch;
	private WebDriver driver;

	public UrlMatchesExpectation(WebDriver driver, String urlToMatch, String detail) {
		this.urlToMatch = urlToMatch;
		this.driver = driver;
		this.detail = detail;
	}

	@Override
	public Boolean apply(WebDriver input) {
		return StringUtils.contains(driver.getCurrentUrl(), urlToMatch);
	}

	@Override
	public String toString() {
		return detail;
	}

}
