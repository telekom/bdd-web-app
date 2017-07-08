package de.telekom.pages;

import de.telekom.test.frontend.element.WebElementEnhanced;
import de.telekom.test.frontend.page.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ReservationPage extends Page {

    public static final String URL = "reservation";

    @FindBy(id = "departure")
    private WebElementEnhanced departureInput;

    @FindBy(id = "startTime")
    private WebElementEnhanced startTimeInput;

    @FindBy(id = "destination")
    private WebElementEnhanced destinationInput;

    @FindBy(id = "endTime")
    private WebElementEnhanced endTimeInput;

    public ReservationPage(WebDriver driver) {
        super(driver);
    }

    public void setDeparture(String departure) {
        departureInput.setValue(departure);
    }

    public void setStartTime(String startTime) {
        startTimeInput.setValue(startTime);
    }

    public void setDestination(String destination) {
        destinationInput.setValue(destination);
    }

    public void setEndTime(String endTime) {
        endTimeInput.setValue(endTime);
    }

    @Override
    public String getURL() {
        return URL;
    }

}
