package de.telekom.test.bddwebapp.taxi.pages;

import de.telekom.test.bddwebapp.frontend.element.WebElementEnhanced;
import de.telekom.test.bddwebapp.frontend.page.JQueryPage;
import groovy.util.logging.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
public class RegistrationPage extends JQueryPage {

    public static final String URL = "registration";

    @FindBy(id = "firstName")
    private WebElementEnhanced firstNameInput;

    @FindBy(id = "lastName")
    private WebElementEnhanced lastNameInput;

    @FindBy(id = "username")
    private WebElementEnhanced usernameInput;

    @FindBy(id = "password")
    private WebElementEnhanced passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElementEnhanced submitButton;

    @FindBy(css = "input:invalid")
    private WebElementEnhanced validationError;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void setFirstName(String firstName) {
        firstNameInput.setValue(firstName);
    }

    public void setLastName(String lastName) {
        lastNameInput.setValue(lastName);
    }

    public void setUsername(String username) {
        usernameInput.setValue(username);
    }

    public void setPassword(String password) {
        passwordInput.setValue(password);
    }

    public void submitRegistration() {
        submitButton.click();
    }

    public boolean registrationDataIsInvalidMessageIsShown() {
        // The validation error is not displayed in html unit
        if (driver instanceof HtmlUnitDriver) {
            return true;
        }
        return validationError.exists() && validationError.isDisplayed();
    }

    @Override
    public String getURL() {
        return URL;
    }

}
