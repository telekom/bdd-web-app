package de.telekom.test.bddwebapp.taxi.pages;

import de.telekom.test.bddwebapp.frontend.page.JQueryPage;
import groovy.util.logging.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
public class RegistrationPage extends JQueryPage {

    public static final String URL = "registration";

    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(css = "input:invalid")
    private WebElement validationError;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void setFirstName(String firstName) {
        setValue(firstNameInput, firstName);
    }

    public void setLastName(String lastName) {
        setValue(lastNameInput, lastName);
    }

    public void setUsername(String username) {
        setValue(usernameInput, username);
    }

    public void setPassword(String password) {
        setValue(passwordInput, password);
    }

    public void submitRegistration() {
        submitButton.click();
    }

    public boolean registrationDataIsInvalidMessageIsShown() {
        return exists(validationError) && validationError.isDisplayed();
    }

    @Override
    public String getURL() {
        return URL;
    }

}
