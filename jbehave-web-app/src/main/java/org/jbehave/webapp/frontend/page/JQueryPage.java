package org.jbehave.webapp.frontend.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Abstract base class for page objects for jquery frontends.
 *
 * @author Daniel Keiss
 */
public abstract class JQueryPage extends Page {

    public JQueryPage(WebDriver driver) {
        super(driver);
    }

    protected synchronized void waitForAjaxToComplete() {
        ExpectedCondition<Boolean> noAjaxRequestActive = (WebDriver webDriver) -> {
            try {
                return (Boolean) ((JavascriptExecutor) webDriver).executeScript("return jQuery.active == 0");
            } catch (WebDriverException e) {
                return true;
            }
        };

        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        wait.until(noAjaxRequestActive);
    }

}
