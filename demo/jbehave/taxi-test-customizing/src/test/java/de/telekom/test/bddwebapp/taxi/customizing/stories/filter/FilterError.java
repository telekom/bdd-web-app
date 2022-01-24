package de.telekom.test.bddwebapp.taxi.customizing.stories.filter;

import de.telekom.test.bddwebapp.jbehave.stories.customizing.CurrentStoryEmbedderMonitor;
import de.telekom.test.bddwebapp.taxi.customizing.config.AbstractTestLevelStory;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.UnderscoredToCapitalized;
import org.jbehave.core.reporters.FreemarkerViewGenerator;

import java.nio.charset.StandardCharsets;

import static java.util.Arrays.asList;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class FilterError extends AbstractTestLevelStory {

    @Override
    public Configuration configuration() {
        var configuration = new MostUsefulConfiguration();
        configuration.useStoryReporterBuilder(screenshotStoryReporterBuilder());
        configuration.useStoryPathResolver(removeStoryFromClassNameStoryPathResolver());
        configuration.useViewGenerator(new FreemarkerViewGenerator(new UnderscoredToCapitalized(), FreemarkerViewGenerator.class, StandardCharsets.UTF_8));
        configuration.storyControls().doMetaByRow(true);
        return configuration;
    }

    @Override
    public Embedder configuredEmbedder() {
        var embedder = super.configuredEmbedder();
        embedder.useEmbedderMonitor(new CurrentStoryEmbedderMonitor(getApplicationContext()));

        // deactivate view generation for single story runs to prevent false positive
        if (isExecutedByJUnitRunner()) {
            embedder.embedderControls().doGenerateViewAfterStories(false);
        }

        // adding meta filter support for -DmetaFilters=...
        metaFilters().ifPresent(metaFilters -> {
            embedder.useMetaFilters(asList(metaFilters.split(";")));
            supportForSingleScenarioExecution(embedder);
        });

        embedder.useMetaFilters(asList("+execute successful"));

        return embedder;
    }

}
