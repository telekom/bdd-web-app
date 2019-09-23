package de.telekom.test.bddwebapp.stories.customizing;

import de.telekom.test.bddwebapp.api.ApiOnly;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverConfiguration;
import de.telekom.test.bddwebapp.steps.RestartBrowserBeforeScenario;
import de.telekom.test.bddwebapp.stories.config.AlternativeWebDriverConfiguration;
import lombok.Getter;
import lombok.Setter;
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
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class CurrentStory {

    @Autowired
    private CustomizingStories customizingStories;

    @Getter
    @Setter
    private String storyPath;

    public String getStoryName() {
        if (storyPath == null) {
            return null;
        }
        return storyPath.substring(storyPath.lastIndexOf("/") + 1, storyPath.lastIndexOf("."));
    }

    public Class getStoryClass() {
        String storyName = getStoryName();
        if (storyName == null) {
            return null;
        }
        return customizingStories.getStoryClass(storyName);
    }

    public boolean isApiOnly() {
        return isApiOnlyForAllStories() ||
                isApiOnlyBaseType() ||
                isApiOnlyAnnotationForCurrentStory();
    }

    private boolean isApiOnlyForAllStories() {
        return customizingStories.isApiOnlyForAllStories();
    }

    private boolean isApiOnlyBaseType() {
        Class clazz = getStoryClass();
        return clazz != null &&
                customizingStories.getApiOnlyBaseType() != null &&
                customizingStories.getApiOnlyBaseType().isAssignableFrom(clazz);
    }

    private boolean isApiOnlyAnnotationForCurrentStory() {
        Class clazz = getStoryClass();
        return clazz != null &&
                stream(clazz.getAnnotations()).anyMatch(a -> a.annotationType().equals(ApiOnly.class));
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
        Class clazz = getStoryClass();
        return clazz != null &&
                customizingStories.getRestartBrowserBeforeScenarioBaseType() != null &&
                customizingStories.getRestartBrowserBeforeScenarioBaseType().isAssignableFrom(clazz);
    }

    private boolean isRestartBrowserBeforeScenarioForCurrentStory() {
        Class clazz = getStoryClass();
        return clazz != null && stream(clazz.getAnnotations())
                .anyMatch(a -> a.annotationType().equals(RestartBrowserBeforeScenario.class));
    }

    public Optional<Class<? extends WebDriverConfiguration>> getAlternativeWebDriverConfiguration() {
        Class clazz = getStoryClass();
        Annotation alternativeWebDriverConfiguration;
        if (clazz != null && (alternativeWebDriverConfiguration = clazz.getAnnotation(AlternativeWebDriverConfiguration.class)) != null) {
            return Optional.of(((AlternativeWebDriverConfiguration) alternativeWebDriverConfiguration).value());
        }
        return Optional.empty();
    }

}
