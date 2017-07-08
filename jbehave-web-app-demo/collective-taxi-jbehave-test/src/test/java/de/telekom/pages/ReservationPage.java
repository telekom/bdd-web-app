package de.telekom.pages;

import de.telekom.test.frontend.page.Page;
import org.openqa.selenium.WebDriver;

public class ReservationPage extends Page {

    public static final String URL = "reservation";

    public ReservationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getURL() {
        return URL;
    }

}
