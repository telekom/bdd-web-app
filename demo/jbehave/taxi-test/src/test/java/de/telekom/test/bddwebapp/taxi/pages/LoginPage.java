package de.telekom.test.bddwebapp.taxi.pages;

import de.telekom.test.bddwebapp.frontend.page.JQueryPage;
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
public class LoginPage extends JQueryPage {

    public static final String URL = "login";

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(partialLinkText = "Register")
    private WebElement registrationLink;

    @FindBy(className = "alert-success")
    private WebElement alertSuccessDiv;

    @FindBy(className = "alert-warning")
    private WebElement alertWarningDiv;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void setUsername(String username) {
        setValue(usernameInput, username);
    }

    public void setPassword(String password) {
        setValue(passwordInput, password);
    }

    public void submitLogin() {
        submitButton.click();
    }

    public void clickRegistration() {
        registrationLink.click();
    }

    public boolean registeredMessageIsShown() {
        waitForExisting(alertSuccessDiv, 1);
        return alertSuccessDiv.getText().contains("registered");
    }

    public boolean loginDataIsInvalidMessageIsShown() {
        waitForExisting(alertWarningDiv, 1);
        return alertWarningDiv.getText().contains("invalid");
    }

    @Override
    public String getURL() {
        return URL;
    }

}
