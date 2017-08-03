package de.telekom.jbehave.webapp.frontend.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import static org.apache.commons.lang3.StringUtils.contains;

/**
 * ExpectedCondition for url matching.
 *
 * @author Daniel Keiss
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
        return contains(driver.getCurrentUrl(), urlToMatch);
    }

    @Override
    public String toString() {
        return detail;
    }

}
