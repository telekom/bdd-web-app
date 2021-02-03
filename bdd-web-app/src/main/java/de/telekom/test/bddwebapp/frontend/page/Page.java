package de.telekom.test.bddwebapp.frontend.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Abstract base class for page objects. Checks the current url when creating.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Tim Jödicke - First implementation of checkPageState() in project using bdd-web-app
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
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
        Wait<WebDriver> wait = new WebDriverWait(driver, 15);
        wait.until(new UrlMatchesExpectation(getURL(), this.getClass().getName()));
    }

    /**
     * Check if the page is completely loaded.
     * Used by checkPage().
     */
    public void checkPageState() {
        if (driver instanceof JavascriptExecutor) {
            Wait<WebDriver> wait = new WebDriverWait(driver, 15);
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

}
