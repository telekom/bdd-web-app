package de.telekom.test.bddwebapp.frontend.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Abstract base class for page objects. Checks the current url when creating.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Tim Jödicke - First implementation of checkPageState() in project using bdd-web-app
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public abstract class Page {

    protected final WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Check if the page is the right one.
     * By default, the URL is checked.
     * If necessary, another check can be used by implementing checkPageDesignator(), e.g. for single page applications.
     */
    public void checkPage() {
        checkUrl();
        checkPageState();
        checkPageDesignator();
    }

    /**
     * Check if the expected URL matches the current one.
     * Used by checkPage().
     */
    public void checkUrl() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(new UrlMatchesExpectation(getURL(), this.getClass().getName()));
    }

    /**
     * Check if the page is completely loaded.
     * Used by checkPage().
     */
    public void checkPageState() {
        if (driver instanceof JavascriptExecutor) {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        }
    }

    /**
     * Override this if you wan't to assert the page by a web element, e.g. in single page applications.
     * Used by checkPage().
     */
    public void checkPageDesignator() {
        // implement your check
    }

    public void reload() {
        driver.navigate().refresh();
    }

    public void waitFor(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * At page instantiation it's checked that this URL is contained in the open browser URL.
     * You can use regular expressions like "/path/.+/path".
     * So be careful with query params, you have to mask "?" by "\?".
     */
    public abstract String getURL();

    public String getHtml(WebElement webElement) {
        return webElement.getAttribute("innerHTML");
    }

    public void waitFor(Function<WebDriver, Boolean> function, int maxWaitTimeInSeconds, String errorMessage) {
        WebDriverWait WebDriverWait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitTimeInSeconds));
        WebDriverWait.withMessage(errorMessage);
        WebDriverWait.until(function);
    }

    public void waitForExisting(WebElement webElement, int maxWaitTimeInSeconds) {
        Function<WebDriver, Boolean> waitForExisting = webDriver -> exists(webElement);
        waitFor(waitForExisting, maxWaitTimeInSeconds, "Element still not exists!");
    }

    public void waitForDisplayed(WebElement webElement, int maxWaitTimeInSeconds) {
        Function<WebDriver, Boolean> waitForDisplayed = webDriver -> {
            try {
                return webElement.isDisplayed();
            } catch (WebDriverException e) {
                return false;
            }
        };
        waitFor(waitForDisplayed, maxWaitTimeInSeconds, "Element: \"" + webElement + "\" is still not displayed!");
    }

    public void scrollTo(WebElement webElement) {
        if (isJQueryAvailable(webElement)) {
            scrollToWithJQuery(webElement);
        } else {
            scrollToWithDefaultJavaScript(webElement);
        }
    }

    public boolean isJQueryAvailable(WebElement webElement) {
        return (boolean) ((JavascriptExecutor) driver).executeScript("return typeof jQuery != 'undefined'", webElement);
    }

    public void scrollToWithJQuery(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, $(arguments[0]).offset().top - (window.innerHeight / 2))", webElement);
        waitForDisplayed(webElement, 1);
    }

    public void scrollToWithDefaultJavaScript(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", webElement);
        waitForDisplayed(webElement, 1);
        // try it with a different strategy
        if (!webElement.isDisplayed()) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, arguments[0]);", webElement);
            waitForDisplayed(webElement, 1);
        }
    }

    public void click(WebElement webElement) {
        click(webElement, true);
    }

    public void click(WebElement webElement, boolean scrollTo) {
        if (scrollTo) {
            scrollTo(webElement);
        }
        webElement.click();
    }

    public void setValue(WebElement webElement, String value) {
        webElement.clear();
        webElement.sendKeys(value);
    }

    public boolean exists(WebElement webElement) {
        return check(o -> webElement.isEnabled());
    }

    public boolean hasChildren(WebElement webElement, By by) {
        return check(o -> webElement.findElement(by));
    }

    public boolean check(Function check) {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        try {
            check.apply(Void.TYPE);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        }
        return true;
    }

}
