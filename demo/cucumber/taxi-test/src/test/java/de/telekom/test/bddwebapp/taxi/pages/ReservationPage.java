package de.telekom.test.bddwebapp.taxi.pages;

import de.telekom.test.bddwebapp.frontend.page.JQueryPage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Function;

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
    private WebElement dateInput;

    @FindBy(id = "departure")
    private WebElement departureInput;

    @FindBy(id = "earliestStartTime")
    private WebElement earliestStartTimeInput;

    @FindBy(id = "destination")
    private WebElement destinationInput;

    @FindBy(id = "latestStartTime")
    private WebElement latestStartTimeInput;

    @FindBy(id = "reserve")
    private WebElement reserveButton;

    @FindBy(id = "reservation")
    private WebElement reservationDiv;

    public ReservationPage(WebDriver driver) {
        super(driver);
    }

    public void setDate(String date) {
        setValue(dateInput, date);
    }

    public void setDeparture(String departure) {
        setValue(departureInput, departure);
    }

    public void setEarliestStartTime(String earliestStartTime) {
        setValue(earliestStartTimeInput, earliestStartTime);
    }

    public void setDestination(String destination) {
        setValue(destinationInput, destination);
    }

    public void setLatestStartTime(String latestStartTime) {
        setValue(latestStartTimeInput, latestStartTime);
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
        Function<WebDriver, Boolean> waitForUpdate = webDriver -> {
            try {
                WebElement tr = reservationDiv.findElement(By.tagName("tr"));
                tr.findElements(By.tagName("td"));
                return true;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        };
        waitFor(waitForUpdate, 2, "Table is still stale!");

        var tableRows = reservationDiv.findElements(By.tagName("tr"));
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
