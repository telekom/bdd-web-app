package de.telekom.test.bddwebapp.frontend.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

/**
 * Abstract base class for page objects for jquery frontends.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public abstract class JQueryPage extends Page {

    public JQueryPage(WebDriver driver) {
        super(driver);
    }

    public synchronized void waitForAjaxToComplete() {
        ExpectedCondition<Boolean> noAjaxRequestActive = (WebDriver webDriver) -> {
            try {
                return (Boolean) ((JavascriptExecutor) Objects.requireNonNull(webDriver)).executeScript("return jQuery.active === 0");
            } catch (WebDriverException e) {
                return true;
            }
        };

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(noAjaxRequestActive);
    }

}
