package de.telekom.test.bddwebapp.jbehave.stories;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;
import de.telekom.test.bddwebapp.jbehave.steps.ScannedStepsFactory;
import de.telekom.test.bddwebapp.jbehave.stories.config.FaultTolerantStoryPathResolver;
import de.telekom.test.bddwebapp.jbehave.stories.config.ScannedStoryPaths;
import de.telekom.test.bddwebapp.jbehave.stories.config.ScreenshotStoryReporterBuilder;
import de.telekom.test.bddwebapp.jbehave.stories.customizing.CurrentStoryEmbedderMonitor;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.UnderscoredToCapitalized;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Run all stories
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RunWith(JUnitReportingRunner.class)
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
        return embedder;
    }

    public abstract ApplicationContext getApplicationContext();

}
