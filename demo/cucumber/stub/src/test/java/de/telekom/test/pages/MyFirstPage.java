package de.telekom.test.pages;

import de.telekom.test.bddwebapp.frontend.page.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class MyFirstPage extends Page {

    @FindBy(id = "myId")
    private WebElement webElement;

    public MyFirstPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getURL() {
        return "";
    }

}
