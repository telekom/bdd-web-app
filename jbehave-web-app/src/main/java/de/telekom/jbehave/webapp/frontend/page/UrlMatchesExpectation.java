package de.telekom.jbehave.webapp.frontend.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * ExpectedCondition for url matching.
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public class UrlMatchesExpectation implements ExpectedCondition<Boolean> {

    private final String detail;
    private final String urlToMatch;

    public UrlMatchesExpectation(String urlToMatch, String detail) {
        this.urlToMatch = urlToMatch;
        this.detail = detail;
    }

    @Override
    public Boolean apply(WebDriver driver) {
        String regex = ".*" + urlToMatch + ".*"; // contains
        return driver.getCurrentUrl().matches(regex);
    }

    @Override
    public String toString() {
        return detail;
    }

}
