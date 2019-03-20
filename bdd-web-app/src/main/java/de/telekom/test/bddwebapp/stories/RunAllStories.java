package de.telekom.test.bddwebapp.stories;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;
import de.telekom.test.bddwebapp.api.ApiOnly;
import de.telekom.test.bddwebapp.steps.ScannedStepsFactory;
import de.telekom.test.bddwebapp.stories.config.FaultTolerantStoryPathResolver;
import de.telekom.test.bddwebapp.stories.config.ScannedStoryPaths;
import de.telekom.test.bddwebapp.stories.config.ScreenshotStoryReporterBuilder;
import de.telekom.test.bddwebapp.stories.customizing.CurrentStoryEmbedderMonitor;
import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static java.util.Arrays.stream;

/**
 * Run all stories
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RunWith(JUnitReportingRunner.class)
public abstract class RunAllStories extends JUnitStories implements ScannedStepsFactory, ScreenshotStoryReporterBuilder, FaultTolerantStoryPathResolver, ScannedStoryPaths {

    @Override
    public Configuration configuration() {
        Configuration configuration = new MostUsefulConfiguration();
        configuration.useStoryReporterBuilder(screenshotStoryReporterBuilder());
        configuration.useStoryPathResolver(removeStoryFromClassNameStoryPathResolver());
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
        Embedder embedder = super.configuredEmbedder();
        embedder.useEmbedderMonitor(new CurrentStoryEmbedderMonitor(getApplicationContext()));
        return embedder;
    }

    @Override
    public void run() {
        if (apiOnly()) {
            CustomizingStories storyClasses = getApplicationContext().getBean(CustomizingStories.class);
            storyClasses.setApiOnlyForAllStories(true);
        }
        super.run();
    }

    /**
     * Check this if instance has the @ApiOnly annotation
     */
    public boolean apiOnly() {
        return stream(this.getClass().getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().equals(ApiOnly.class));
    }

    public abstract ApplicationContext getApplicationContext();

}
