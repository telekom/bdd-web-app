package de.telekom.test.bddwebapp.stories;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;
import com.google.common.collect.Lists;
import de.telekom.test.bddwebapp.frontend.screenshot.ScreenshotReportForm;
import de.telekom.test.bddwebapp.steps.Steps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Basis story
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RunWith(JUnitReportingRunner.class)
public abstract class AbstractStory extends JUnitStory {

    public Configuration defaultConfiguration() {
        Configuration configuration = new MostUsefulConfiguration();
        configuration.useStoryReporterBuilder(defaultStoryReporterBuilder());
        configuration.useStoryPathResolver(defaultStoryPathResolver());
        return configuration;
    }

    public StoryReporterBuilder defaultStoryReporterBuilder() {
        Format screenshootingHtmlFormat = getApplicationContext().getBean(ScreenshotReportForm.class);
        return new StoryReporterBuilder().withCrossReference(new CrossReference())
                .withFormats(Format.TXT, Format.CONSOLE, Format.STATS, Format.XML, screenshootingHtmlFormat)
                .withCodeLocation(CodeLocations.codeLocationFromClass(getClass())).withFailureTrace(true);
    }

    public StoryPathResolver defaultStoryPathResolver() {
        return new UnderscoredCamelCaseResolver().removeFromClassName("Story");
    }

    @Override
    public Configuration configuration() {
        return defaultConfiguration();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        List<Object> steps = Lists.newArrayList(getApplicationContext().getBeansWithAnnotation(Steps.class).values());
        return new InstanceStepsFactory(configuration(), steps);
    }

    public abstract ApplicationContext getApplicationContext();

}
