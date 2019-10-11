package de.telekom.test.bddwebapp.frontend.element;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Extends WebElement by several helper methods.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Tim Jödicke
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class WebElementEnhanced extends WebElementProxy {

    public static List<String> NOT_INVOKE_WEB_ELEMENT_METHODS = Arrays.asList("setWebElement", "setWebDriver", "exists", "check");

    protected WebDriver webDriver;

    public WebElementEnhanced() {
    }

    public WebElementEnhanced(WebElement webElement, WebDriver webDriver) {
        super(webElement);
        this.webDriver = webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public List<WebElementEnhanced> findElementsEnhanced(By by) {
        return webElement.findElements(by).stream()
                .map(element -> new WebElementEnhanced(element, webDriver))
                .collect(toList());
    }

    public String getHtml() {
        return webElement.getAttribute("innerHTML");
    }

    public WebElementEnhanced findElementEnhanced(By by) {
        return new WebElementEnhanced(webElement.findElement(by), webDriver);
    }

    public void waitFor(Function<WebDriver, Boolean> function, int maxWaitTimeInSeconds, String errorMessage) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, maxWaitTimeInSeconds);
        webDriverWait.withMessage(errorMessage);
        webDriverWait.until(function);
    }

    public void waitForExisting(int maxWaitTimeInSeconds) {
        Function<WebDriver, Boolean> waitForExisting = driver -> exists();
        waitFor(waitForExisting, maxWaitTimeInSeconds, "Element still not exists!");
    }

    public void waitForDisplayed(int maxWaitTimeInSeconds) {
        Function<WebDriver, Boolean> waitForDisplayed = driver -> {
            try {
                return webElement.isDisplayed();
            } catch (WebDriverException e) {
                return false;
            }
        };
        waitFor(waitForDisplayed, maxWaitTimeInSeconds, "Element: \"" + webElement + "\" is still not displayed!");
    }

    public void scrollTo() {
        if (isJQueryAvailable()) {
            scrollToWithJQuery();
        } else {
            scrollToWithDefaultJavaScript();
        }
    }

    public boolean isJQueryAvailable() {
        return (boolean) ((JavascriptExecutor) webDriver).executeScript("return typeof jQuery != 'undefined'", webElement);
    }

    public void scrollToWithJQuery() {
        ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, $(arguments[0]).offset().top - (window.innerHeight / 2))", webElement);
        waitForDisplayed(1);
    }

    public void scrollToWithDefaultJavaScript() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webElement);
        waitForDisplayed(1);
        // try it with a different strategy
        if (!isDisplayed()) {
            ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, arguments[0]);", webElement);
            waitForDisplayed(1);
        }
    }

    public void click() {
        click(true);
    }

    public void click(boolean scrollTo) {
        if (scrollTo) {
            scrollTo();
        }
        webElement.click();
    }

    public void setValue(String value) {
        clear();
        sendKeys(value);
    }

    public boolean exists() {
        return check(o -> getWebElement() /* invoke web element */);
    }

    public boolean hasChildren(By by) {
        return check(o -> webElement.findElement(by));
    }

    public boolean check(Function check) {
        webDriver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
        try {
            check.apply(Void.TYPE);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        } finally {
            webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
        return true;
    }

}
