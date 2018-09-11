package de.telekom.test.bddwebapp.taxi.config;

import de.telekom.test.bddwebapp.stories.AbstractStory;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public abstract class AbstractTaxiStory extends AbstractStory {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

}
