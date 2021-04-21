package de.telekom.test.bddwebapp.taxi.pages;

import de.telekom.test.bddwebapp.frontend.element.WebElementEnhanced;
import de.telekom.test.bddwebapp.frontend.page.JQueryPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class LoginPage extends JQueryPage {

    public static final String URL = "login";

    @FindBy(id = "username")
    private WebElementEnhanced usernameInput;

    @FindBy(id = "password")
    private WebElementEnhanced passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElementEnhanced submitButton;

    @FindBy(partialLinkText = "Register")
    private WebElementEnhanced registrationLink;

    @FindBy(className = "alert-success")
    private WebElementEnhanced alertSuccessDiv;

    @FindBy(className = "alert-warning")
    private WebElementEnhanced alertWarningDiv;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void setUsername(String username) {
        usernameInput.setValue(username);
    }

    public void setPassword(String password) {
        passwordInput.setValue(password);
    }

    public void submitLogin() {
        submitButton.click();
    }

    public void clickRegistration() {
        registrationLink.click();
    }

    public boolean registeredMessageIsShown() {
        alertSuccessDiv.waitForExisting(1);
        return alertSuccessDiv.getText().contains("registered");
    }

    public boolean loginDataIsInvalidMessageIsShown() {
        alertWarningDiv.waitForExisting(1);
        return alertWarningDiv.getText().contains("invalid");
    }

    @Override
    public String getURL() {
        return URL;
    }

}
