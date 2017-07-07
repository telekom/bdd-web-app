package de.telekom.steps;

import de.telekom.pages.LoginPage;
import de.telekom.test.frontend.steps.SeleniumSteps;
import de.telekom.test.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.springframework.beans.factory.annotation.Value;

@Steps
public class UserSessionSteps extends SeleniumSteps {

    @Given("ein eingeloggter Kunde $customer")
    public void loggedInCustomer(String customer){
        open(getUrlWithHost("localhost:8080", "", LoginPage.URL));

        LoginPage loginPage = createExpectedPage(LoginPage.class);
        loginPage.setUsername(customer);
        loginPage.setPassword("test1234");
        loginPage.submitLogin();
    }

}
