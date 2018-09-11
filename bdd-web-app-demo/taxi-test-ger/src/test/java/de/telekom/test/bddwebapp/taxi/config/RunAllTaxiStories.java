package de.telekom.test.bddwebapp.taxi.config;

import de.telekom.test.bddwebapp.stories.RunAllStories;
import org.jbehave.core.configuration.Configuration;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public class RunAllTaxiStories extends RunAllStories implements GermanKeywordsConfiguration {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public String storiesBasePath() {
        return "de.telekom.test.bddwebapp.taxi.stories";
    }

    @Override
    public Configuration configuration() {
        return germanKeywordsConfiguration();
    }

}
