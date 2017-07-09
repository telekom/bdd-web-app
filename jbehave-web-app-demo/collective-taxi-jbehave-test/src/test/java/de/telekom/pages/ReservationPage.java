package de.telekom.pages;

import de.telekom.test.frontend.element.WebElementEnhanced;
import de.telekom.test.frontend.page.Page;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReservationPage extends AbstractCollectiveTaxiPage {

    public static final String URL = "reservation";

    @FindBy(id = "departure")
    private WebElementEnhanced departureInput;

    @FindBy(id = "startTime")
    private WebElementEnhanced startTimeInput;

    @FindBy(id = "destination")
    private WebElementEnhanced destinationInput;

    @FindBy(id = "endTime")
    private WebElementEnhanced endTimeInput;

    @FindBy(id = "reserve")
    private WebElementEnhanced reserveButton;

    @FindBy(id = "reservation")
    private WebElementEnhanced reservationDiv;

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

    public void submitReservation() {
        reserveButton.click();
    }

    public boolean isReservationSuccess() {
        waitForAjaxToComplete();
        return reservationDiv.getText().contains("erfolgreich");
    }

    @Override
    public String getURL() {
        return URL;
    }
}
