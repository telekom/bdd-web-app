package de.telekom.test.bddwebapp.frontend.steps;

import de.telekom.test.bddwebapp.frontend.page.Page;
import org.openqa.selenium.WebDriver;

public class AnyPage extends Page {

    public AnyPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getURL() {
        return "https://github.com/telekom/bdd-web-app";
    }

}
