package de.telekom.pages;

import de.telekom.test.frontend.element.WebElementEnhanced;
import de.telekom.test.frontend.page.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {

    public static final String URL = "login";

    @FindBy(id = "username")
    private WebElementEnhanced usernameInput;

    @FindBy(id = "password")
    private WebElementEnhanced passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElementEnhanced submitElement;

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
        submitElement.scrollTo();
        submitElement.click();
    }

    @Override
    public String getURL() {
        return URL;
    }

}
