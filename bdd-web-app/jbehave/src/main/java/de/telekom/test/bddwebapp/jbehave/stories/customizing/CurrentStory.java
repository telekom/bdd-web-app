package de.telekom.test.bddwebapp.jbehave.stories.customizing;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverConfiguration;
import de.telekom.test.bddwebapp.jbehave.steps.RestartBrowserBeforeScenario;
import de.telekom.test.bddwebapp.jbehave.stories.config.AlternativeWebDriverConfiguration;
import org.jbehave.core.model.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Optional;

import static java.util.Arrays.stream;

/**
 * Gives access to the current story about the Spring Context
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Marc Eckart - Initial implementation of current story bean
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class CurrentStory {

    private final ThreadLocal<String> storyPath = new ThreadLocal<>();
    private final ThreadLocal<Meta> storyMetaData = new ThreadLocal<>();

    @Autowired
    private CustomizingStories customizingStories;

    public String getStoryPath() {
        return storyPath.get();
    }

    public void setStoryPath(String storyPath) {
        this.storyPath.set(storyPath);
    }

    public Meta getStoryMetaData() {
        return storyMetaData.get();
    }

    public void setStoryMetaData(Meta storyMetaData) {
        this.storyMetaData.set(storyMetaData);
    }

    public String getStoryName() {
        var storyPath = getStoryPath();
        if (storyPath == null) {
            return null;
        }
        return storyPath.substring(storyPath.lastIndexOf("/") + 1, storyPath.lastIndexOf("."));
    }

    public Class getStoryClass() {
        var storyName = getStoryName();
        if (storyName == null) {
            return null;
        }
        return customizingStories.getStoryClass(storyName);
    }

    public boolean isRestartBrowserBeforeScenario() {
        return isRestartBrowserBeforeScenarioForAllStories() ||
                isRestartBrowserBeforeScenarioBaseType() ||
                isRestartBrowserBeforeScenarioForCurrentStory();
    }

    private boolean isRestartBrowserBeforeScenarioForAllStories() {
        return customizingStories.isRestartBrowserBeforeScenarioForAllStories();
    }

    private boolean isRestartBrowserBeforeScenarioBaseType() {
        var clazz = getStoryClass();
        return clazz != null &&
                customizingStories.getRestartBrowserBeforeScenarioBaseType() != null &&
                customizingStories.getRestartBrowserBeforeScenarioBaseType().isAssignableFrom(clazz);
    }

    private boolean isRestartBrowserBeforeScenarioForCurrentStory() {
        var clazz = getStoryClass();
        return clazz != null && stream(clazz.getAnnotations())
                .anyMatch(a -> a.annotationType().equals(RestartBrowserBeforeScenario.class));
    }

    public Optional<Class<? extends WebDriverConfiguration>> getAlternativeWebDriverConfiguration() {
        var clazz = getStoryClass();
        Annotation alternativeWebDriverConfiguration;
        if (clazz != null && (alternativeWebDriverConfiguration = clazz.getAnnotation(AlternativeWebDriverConfiguration.class)) != null) {
            return Optional.of(((AlternativeWebDriverConfiguration) alternativeWebDriverConfiguration).value());
        }
        return Optional.empty();
    }

}
