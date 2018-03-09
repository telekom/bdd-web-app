package de.telekom.jbehave.webapp.frontend.element;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Extends WebElement by several helper methods.
 *
 * @author Daniel Keiss, Tim Jödicke
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public class WebElementEnhanced {

    public final static List<String> NOT_INVOKE_WEB_ELEMENT_METHODS = Arrays.asList("setWebElement", "setWebDriver", "exists", "check");

    protected WebElement webElement;
    protected WebDriver webDriver;

    public WebElementEnhanced() {
    }

    public WebElementEnhanced(WebElement webElement) {
        this.webElement = webElement;
    }

    public WebElementEnhanced(WebElement webElement, WebDriver webDriver) {
        this.webElement = webElement;
        this.webDriver = webDriver;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void sendKeys(CharSequence... keysToSend) {
        webElement.sendKeys(keysToSend);
    }

    public Point getLocation() {
        return webElement.getLocation();
    }

    public void submit() {
        webElement.submit();
    }

    public String getAttribute(String name) {
        return webElement.getAttribute(name);
    }

    public String getCssValue(String propertyName) {
        return webElement.getCssValue(propertyName);
    }

    public Dimension getSize() {
        return webElement.getSize();
    }

    public Rectangle getRect() {
        return webElement.getRect();
    }

    public List<WebElement> findElements(By by) {
        return webElement.findElements(by);
    }

    public List<WebElementEnhanced> findElementsEnhanced(By by) {
        return webElement.findElements(by).stream().map(element -> new WebElementEnhanced(element, webDriver)).collect(Collectors.toList());
    }

    public String getText() {
        return webElement.getText();
    }

    public String getTagName() {
        return webElement.getTagName();
    }

    public boolean isSelected() {
        return webElement.isSelected();
    }

    public WebElement findElement(By by) {
        return webElement.findElement(by);
    }

    public WebElementEnhanced findElementEnhanced(By by) {
        return new WebElementEnhanced(webElement.findElement(by), webDriver);
    }

    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    public void clear() {
        webElement.clear();
    }

    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return webElement.getScreenshotAs(outputType);
    }

    public void waitForDisplayed(int maxWaitTimeInSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, maxWaitTimeInSeconds);
        webDriverWait.withMessage("Element: \"" + webElement + "\" is still not displayed!");
        webDriverWait.until(driver -> {
            try {
                return webElement.isDisplayed();
            } catch (WebDriverException e) {
                return false;
            }
        });
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
        new Actions(webDriver).click(webElement).perform();
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
        webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
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
