package de.telekom.test.bddwebapp.taxi.seleniumgrid.config;

import de.telekom.test.bddwebapp.jbehave.stories.RunAllStories;
import de.telekom.test.bddwebapp.taxi.config.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class RunAllTaxiSeleniumGridStories extends RunAllStories {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public String storiesBasePath() {
        return "de.telekom.test.bddwebapp.taxi.seleniumgrid.stories";
    }

}
