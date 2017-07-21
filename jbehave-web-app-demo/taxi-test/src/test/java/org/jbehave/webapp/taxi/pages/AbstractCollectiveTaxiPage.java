package org.jbehave.webapp.taxi.pages;

import org.jbehave.webapp.frontend.page.Page;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractCollectiveTaxiPage extends Page {

    public AbstractCollectiveTaxiPage(WebDriver driver) {
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
