package de.telekom.test.frontend.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Base class for page objects. Page objects represent a certain page of a certain YETI module. It models test relevant interactions and state as an easy to use
 * object.
 * <p>
 * Page objects should provide methods for interactions that return subsequent pages as page objects as well.
 */
public abstract class Page {

    protected WebDriver driver;

    protected Page(WebDriver driver) {
        this.driver = driver;
        checkUrl(driver);
    }

    private void checkUrl(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        wait.until(new UrlMatchesExpectation(driver, getURL(), this.getClass().getName()));
    }

    public void reload() {
        driver.navigate().refresh();
    }

    protected void setValue(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    protected WebElement scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
        ExpectedCondition<Boolean> expectation = arg0 -> {
            try {
                return element.isDisplayed();
            } catch (WebDriverException e) {
                return true;
            }
        };
        Wait<WebDriver> wait = new WebDriverWait(driver, 1);
        wait.until(expectation);
        return element;
    }

    protected void scrollToAndClick(WebElement element) {
        scrollTo(element).click();
    }

    public boolean elementExists(WebElement element) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        try {
            element.getLocation();
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
        return true;
    }

    protected abstract String getURL();

}
