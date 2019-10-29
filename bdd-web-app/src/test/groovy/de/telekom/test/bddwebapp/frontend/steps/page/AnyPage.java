package de.telekom.test.bddwebapp.frontend.steps.page;

import de.telekom.test.bddwebapp.frontend.page.Page;
import org.openqa.selenium.WebDriver;

public class AnyPage extends Page {

    private static String URL; // Don't do that in practise! This is just by unit testing reason

    public AnyPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getURL() {
        return URL;
    }
}
