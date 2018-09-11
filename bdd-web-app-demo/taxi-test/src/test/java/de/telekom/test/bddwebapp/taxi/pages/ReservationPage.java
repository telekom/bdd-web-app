package de.telekom.test.bddwebapp.taxi.pages;

import de.telekom.test.bddwebapp.frontend.element.WebElementEnhanced;
import de.telekom.test.bddwebapp.frontend.page.JQueryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public class ReservationPage extends JQueryPage {

    public static final String URL = "reservation";

    @FindBy(id = "date")
    private WebElementEnhanced dateInput;

    @FindBy(id = "departure")
    private WebElementEnhanced departureInput;

    @FindBy(id = "earliestStartTime")
    private WebElementEnhanced earliestStartTimeInput;

    @FindBy(id = "destination")
    private WebElementEnhanced destinationInput;

    @FindBy(id = "latestStartTime")
    private WebElementEnhanced latestStartTimeInput;

    @FindBy(id = "reserve")
    private WebElementEnhanced reserveButton;

    @FindBy(id = "reservation")
    private WebElementEnhanced reservationDiv;

    public ReservationPage(WebDriver driver) {
        super(driver);
    }

    public void setDate(String date) {
        dateInput.setValue(date);
    }

    public void setDeparture(String departure) {
        departureInput.setValue(departure);
    }

    public void setEarliestStartTime(String earliestStartTime) {
        earliestStartTimeInput.setValue(earliestStartTime);
    }

    public void setDestination(String destination) {
        destinationInput.setValue(destination);
    }

    public void setLatestStartTime(String latestStartTime) {
        latestStartTimeInput.setValue(latestStartTime);
    }

    public void submitReservation() {
        reserveButton.click();
    }

    public boolean isReservationSuccess() {
        waitForAjaxToComplete();
        return reservationDiv.getText().contains("successful");
    }

    public boolean isReservationNotPossible() {
        waitForAjaxToComplete();
        return reservationDiv.getText().contains("not possible");
    }

    public String getPriceBetweenStartAndEndTime(String startTime, String endTime, String passengers) {
        WebElement reservationTable = reservationDiv.findElement(By.className("table"));
        List<WebElement> tableRows = reservationTable.findElements(By.tagName("tr"));
        for (WebElement tableRow : tableRows) {
            List<WebElement> tableCols = tableRow.findElements(By.tagName("td"));
            if (tableCols.isEmpty()) continue; // head
            String currentStartTimeAndEndtime = tableCols.get(0).getText().trim();
            String currentPassengers = tableCols.get(1).getText().trim();
            if (currentStartTimeAndEndtime.contains(startTime) && currentStartTimeAndEndtime.contains(endTime) && currentPassengers.equals(passengers)) {
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
