package de.telekom.test.bddwebapp.frontend.steps.page;

import de.telekom.test.bddwebapp.frontend.page.Page;
import org.openqa.selenium.WebDriver;

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
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
