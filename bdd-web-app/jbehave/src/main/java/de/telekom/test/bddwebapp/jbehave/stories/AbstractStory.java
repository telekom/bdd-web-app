package de.telekom.test.bddwebapp.jbehave.stories;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;
import de.telekom.test.bddwebapp.jbehave.steps.ScannedStepsFactory;
import de.telekom.test.bddwebapp.jbehave.stories.config.FaultTolerantStoryPathResolver;
import de.telekom.test.bddwebapp.jbehave.stories.config.ScreenshotStoryReporterBuilder;
import de.telekom.test.bddwebapp.jbehave.stories.customizing.CurrentStoryEmbedderMonitor;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.io.UnderscoredToCapitalized;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.model.Meta;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

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
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RunWith(JUnitReportingRunner.class)
public abstract class AbstractStory extends JUnitStory implements ScannedStepsFactory, ScreenshotStoryReporterBuilder, FaultTolerantStoryPathResolver {

    @Override
    public Configuration configuration() {
        var configuration = new MostUsefulConfiguration();
        configuration.useStoryReporterBuilder(screenshotStoryReporterBuilder());
        configuration.useStoryPathResolver(removeStoryFromClassNameStoryPathResolver());
        configuration.useViewGenerator(new FreemarkerViewGenerator(new UnderscoredToCapitalized(), FreemarkerViewGenerator.class, StandardCharsets.UTF_8));
        return configuration;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return scannedStepsFactory();
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
            embedder.useMetaFilters(Arrays.asList(metaFilters.split(";")));
            supportForSingleScenarioExecution(embedder);
        });

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

    /**
     * Adding meta matcher for execution of single scenarios, e.g. with the intellij plugin "jbehave debug single scenario"
     */
    public void supportForSingleScenarioExecution(Embedder embedder) {
        embedder.metaMatchers().put("+scenario_title", new MetaFilter.MetaMatcher() {
            private String scenarioTitle;

            @Override
            public void parse(String s) {
                scenarioTitle = s.substring(16);
                scenarioTitle = scenarioTitle.replace("*", ".*");
            }

            @Override
            public boolean match(Meta meta) {
                // scenarios with examples have no title at the first level of the matcher-tree.
                if (!meta.hasProperty("title")) {
                    return true;
                }
                return meta.getProperty("title").trim().matches(scenarioTitle);
            }
        });
    }

    public abstract ApplicationContext getApplicationContext();

}
