package de.telekom.pages;

import de.telekom.test.frontend.element.WebElementEnhanced;
import de.telekom.test.frontend.page.Page;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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

    public String getPriceBetweenStartAndEndTime(String startTime, String endTime) {
        WebElement reservationTable = reservationDiv.findElement(By.className("table"));
        List<WebElement> tableRows = reservationTable.findElements(By.tagName("tr"));
        for (WebElement tableRow : tableRows) {
            List<WebElement> tableCols = tableRow.findElements(By.tagName("td"));
            if (tableCols.isEmpty()) continue; // head
            String currentStartTime = tableCols.get(0).getText().trim();
            String currentEndTime = tableCols.get(1).getText().trim();
            if (startTime.equals(currentStartTime) && endTime.equals(currentEndTime)) {
                return tableCols.get(2).getText().trim();
            }
        }
        return null;
    }

    @Override
    public String getURL() {
        return URL;
    }
}
