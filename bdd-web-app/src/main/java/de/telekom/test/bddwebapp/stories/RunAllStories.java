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
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
public abstract class RunAllStories extends JUnitStories {

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

    @Override
    public List<String> storyPaths() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractStory.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(storiesBasePath());

        List<String> storyPaths = new ArrayList<>();
        for (BeanDefinition component : components) {
            try {
                Class cls = Class.forName(component.getBeanClassName());
                StoryPathResolver pathResolver = configuration().storyPathResolver();
                String storyPath = pathResolver.resolve(cls);
                storyPaths.add(storyPath);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return storyPaths;
    }

    /*
     * Override for better scan performance
     */
    public String storiesBasePath() {
        return "";
    }

    public abstract ApplicationContext getApplicationContext();

}
