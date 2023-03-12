package de.telekom.test.bddwebapp.jbehave.stories;

import de.telekom.test.bddwebapp.jbehave.steps.ScannedStepsFactory;
import de.telekom.test.bddwebapp.jbehave.stories.config.FaultTolerantStoryPathResolver;
import de.telekom.test.bddwebapp.jbehave.stories.config.ScannedStoryPaths;
import de.telekom.test.bddwebapp.jbehave.stories.config.ScreenshotStoryReporterBuilder;
import de.telekom.test.bddwebapp.jbehave.stories.customizing.CurrentStoryEmbedderMonitor;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.UnderscoredToCapitalized;
import org.jbehave.core.junit.JUnit4StoryRunner;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Run all stories
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RunWith(JUnit4StoryRunner.class)
public abstract class RunAllStories extends JUnitStories implements ScannedStepsFactory, ScreenshotStoryReporterBuilder, FaultTolerantStoryPathResolver, ScannedStoryPaths {

    @Override
    public Configuration configuration() {
        var configuration = new MostUsefulConfiguration();
        var storyReporterBuilder = screenshotStoryReporterBuilder();
        configuration.useStoryReporterBuilder(storyReporterBuilder);
        configuration.useStoryPathResolver(removeStoryFromClassNameStoryPathResolver());
        configuration.useViewGenerator(new FreemarkerViewGenerator(new UnderscoredToCapitalized(), FreemarkerViewGenerator.class, StandardCharsets.UTF_8));
        return configuration;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return scannedStepsFactory();
    }

    @Override
    public List<String> storyPaths() {
        return scannedStoryPaths();
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
        metaFilters().ifPresent(metaFilters -> embedder.useMetaFilters(asList(metaFilters.split(";"))));

        return embedder;
    }

    public boolean isExecutedByJUnitRunner() {
        // the test class here is a indicator that the story is run by maven build and not by junit-class
        var testClass = System.getProperty("test");
        return isBlank(testClass);
    }

    public Optional<String> metaFilters() {
        var metaFilters = System.getProperty("metaFilters");
        return Optional.ofNullable(metaFilters);
    }

    public abstract ApplicationContext getApplicationContext();

}
