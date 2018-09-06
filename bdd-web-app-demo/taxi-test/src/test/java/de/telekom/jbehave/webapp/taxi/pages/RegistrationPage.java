package de.telekom.jbehave.webapp.taxi.pages;

import de.telekom.jbehave.webapp.frontend.element.WebElementEnhanced;
import de.telekom.jbehave.webapp.frontend.page.JQueryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
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
        WebElement invalid = driver.findElement(By.cssSelector("input:invalid"));
        return invalid != null && invalid.isDisplayed();
    }

    @Override
    public String getURL() {
        return URL;
    }

}
