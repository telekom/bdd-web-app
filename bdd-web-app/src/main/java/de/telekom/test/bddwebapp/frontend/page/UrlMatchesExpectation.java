package de.telekom.test.bddwebapp.frontend.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * ExpectedCondition for url matching.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
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
