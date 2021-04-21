package de.telekom.test.bddwebapp.taxi.pages;

import de.telekom.test.bddwebapp.frontend.element.WebElementEnhanced;
import de.telekom.test.bddwebapp.frontend.page.JQueryPage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.Optional;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
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

    public Optional<ReservationPrice> getPriceBetweenStartAndEndTime(String startTime, String endTime) {
        var reservationTable = reservationDiv.findElement(By.className("table"));
        var tableRows = reservationTable.findElements(By.tagName("tr"));
        return tableRows.stream()
                .map(tableRow -> tableRow.findElements(By.tagName("td")))
                .filter(tableCols -> {
                    if (tableCols.isEmpty()) {
                        return false; // head
                    }
                    var startTimeAndEndTime = tableCols.get(0).getText().trim();
                    return startTimeAndEndTime.contains(startTime) && startTimeAndEndTime.contains(endTime);
                }).map(tableCols -> {
                    var startTimeAndEndTime = tableCols.get(0).getText().trim();
                    var passengers = tableCols.get(1).getText().trim();
                    var price = tableCols.get(2).getText().trim();
                    return new ReservationPrice(startTimeAndEndTime, passengers, price);
                }).findFirst();
    }

    @Override
    public String getURL() {
        return URL;
    }

    public boolean javaScriptIsDisabled() {
        var currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("?") && currentUrl.contains("date")
                && currentUrl.contains("earliestStartTime") && currentUrl.contains("latestStartTime");
    }

    @AllArgsConstructor
    @Getter
    public class ReservationPrice {

        private final String startTimeAndEndTime;
        private final String passengers;
        private final String price;

    }

}
