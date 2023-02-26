package de.telekom.test.bddwebapp.taxi.htmlunit.config;

import de.telekom.test.bddwebapp.jbehave.stories.AbstractStory;
import de.telekom.test.bddwebapp.taxi.config.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public abstract class AbstractTaxiStory extends AbstractStory {

    @Override
    public ApplicationContext getApplicationContext() {
        System.setProperty("browser", "htmlunit");
        return ApplicationContextProvider.getApplicationContext();
    }

}
