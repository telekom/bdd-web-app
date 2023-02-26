package de.telekom.test.bddwebapp.taxi.ger.config;

import de.telekom.test.bddwebapp.jbehave.stories.AbstractStory;
import org.jbehave.core.configuration.Configuration;
import org.springframework.context.ApplicationContext;

import static de.telekom.test.bddwebapp.taxi.ger.config.GermanKeywordsConfiguration.germanKeywordsConfiguration;

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
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public Configuration configuration() {
        var configuration = germanKeywordsConfiguration();
        configuration.useStoryReporterBuilder(screenshotStoryReporterBuilder());
        return configuration;
    }

}
