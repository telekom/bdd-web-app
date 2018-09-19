package de.telekom.test.bddwebapp.taxi.ger.config;

import de.telekom.test.bddwebapp.stories.AbstractStory;
import org.jbehave.core.configuration.Configuration;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public abstract class AbstractTaxiStory extends AbstractStory implements GermanKeywordsConfiguration {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public Configuration configuration() {
        return germanKeywordsConfiguration();
    }

}
