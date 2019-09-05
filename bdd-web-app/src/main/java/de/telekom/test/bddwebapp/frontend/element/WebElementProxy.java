package de.telekom.test.bddwebapp.frontend.element;

import org.openqa.selenium.*;

import java.util.List;

public abstract class WebElementProxy implements WebElement {

    protected WebElement webElement;

    public WebElementProxy() {
    }

    public WebElementProxy(WebElement webElement) {
        this.webElement = webElement;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
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

}
