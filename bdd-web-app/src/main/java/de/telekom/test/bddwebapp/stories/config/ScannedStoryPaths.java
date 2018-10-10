package de.telekom.test.bddwebapp.stories.config;

import de.telekom.test.bddwebapp.stories.AbstractStory;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.StoryPathResolver;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemClassLoader;
import static org.springframework.util.ClassUtils.resolveClassName;

/**
 * Scan story classes in classpath and add paths for execution
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface ScannedStoryPaths {

    default List<String> scannedStoryPaths() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractStory.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(storiesBasePath());
        StoryPathResolver storyPathResolver = configuration().storyPathResolver();
        return components.stream()
                .map(beanDefinition -> resolveClassName(beanDefinition.getBeanClassName(), getSystemClassLoader()))
                .map(aClass -> storyPathResolver.resolve((Class<? extends Embeddable>) aClass))
                .collect(Collectors.toList());
    }

    default List<String> testLevelStoryPaths(int testLevel) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractStory.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(storiesBasePath());
        StoryPathResolver storyPathResolver = configuration().storyPathResolver();
        List<String> list = new ArrayList<>();
        for (BeanDefinition beanDefinition : components) {
            Class<?> aClass = resolveClassName(beanDefinition.getBeanClassName(), getSystemClassLoader());
            if (containsTestLevel(testLevel, aClass)) {
                String resolve = storyPathResolver.resolve((Class<? extends Embeddable>) aClass);
                list.add(resolve);
            }
        }
        return list;
    }

    // TODO this currently doesn't work
    default boolean containsTestLevel(int testLevel, Class<?> aClass) {
//        if(aClass.getDeclaredAnnotation(StoryRunner.class) == null){
//            return containsTestLevel(testLevel, aClass.getSuperclass());
//        }
        return Arrays.asList(aClass.getDeclaredAnnotation(TestLevel.class).testLevels()).contains(testLevel);
    }

    /*
     * Override for better scan performance
     */
    default String storiesBasePath() {
        return "";
    }

    Configuration configuration();

}
