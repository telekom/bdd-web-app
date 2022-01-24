package de.telekom.test.bddwebapp.taxi.customizing.config;

import de.telekom.test.bddwebapp.jbehave.stories.RunAllStories;
import de.telekom.test.bddwebapp.taxi.config.ApplicationContextProvider;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class RunAllCustomizingTaxiStories extends RunAllStories {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public String storiesBasePath() {
        return "de.telekom.test.bddwebapp.taxi.customizing.stories";
    }

    @Override
    public Configuration configuration() {
        var configuration = super.configuration();
        configuration.storyControls().doMetaByRow(true);
        return configuration;
    }

    @Override
    public Embedder configuredEmbedder() {
        var embedder = super.configuredEmbedder();
        embedder.useMetaFilters(List.of("+execute successful"));
        return embedder;
    }

}
