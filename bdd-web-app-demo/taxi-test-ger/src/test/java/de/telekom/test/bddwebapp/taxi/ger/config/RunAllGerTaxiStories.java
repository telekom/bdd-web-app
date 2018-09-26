package de.telekom.test.bddwebapp.taxi.ger.config;

import de.telekom.test.bddwebapp.stories.RunAllStories;
import org.jbehave.core.configuration.Configuration;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class RunAllGerTaxiStories extends RunAllStories implements GermanKeywordsConfiguration {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public String storiesBasePath() {
        return "de.telekom.test.bddwebapp.taxi.ger.stories";
    }

    @Override
    public Configuration configuration() {
        return germanKeywordsConfiguration();
    }

}
