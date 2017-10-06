package de.telekom.jbehave.webapp.taxi.config;

import de.telekom.jbehave.webapp.stories.RunAllStories;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public class RunAllTaxiStories extends RunAllStories {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public String storiesBasePath() {
        return "de.telekom.jbehave.webapp.taxi.stories";
    }

}
