package de.telekom.test.bddwebapp.stories;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;
import de.telekom.test.bddwebapp.steps.ScannedStepsFactory;
import de.telekom.test.bddwebapp.stories.config.FaultTolerantStoryPathResolver;
import de.telekom.test.bddwebapp.stories.config.ScreenshotStoryReporterBuilder;
import de.telekom.test.bddwebapp.stories.customizing.CurrentStoryEmbedderMonitor;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Add the following features to the story execution:
 * - Screenshot build
 * - Story path hardening
 * - Steps factory to find steps by annotation
 * - Most useful embedder configuration for the test framework
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Nils Villwock - Idea to disable report generation after story execution to prevent false positive
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RunWith(JUnitReportingRunner.class)
public abstract class AbstractStory extends JUnitStory implements ScannedStepsFactory, ScreenshotStoryReporterBuilder, FaultTolerantStoryPathResolver {

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
    public Embedder configuredEmbedder() {
        Embedder embedder = super.configuredEmbedder();
        embedder.useEmbedderMonitor(new CurrentStoryEmbedderMonitor(getApplicationContext()));

        if (isExecutedByJUnitRunner()) {
            // deactivate view generation for single story runs to prevent false positive
            embedder.embedderControls().doGenerateViewAfterStories(false);
        }

        return embedder;
    }

    public boolean isExecutedByJUnitRunner() {
        // the test class here is a indicator that the story is run by maven build and not by junit-class
        String testClass = System.getProperty("test");
         return isBlank(testClass);
    }

    public abstract ApplicationContext getApplicationContext();

}
