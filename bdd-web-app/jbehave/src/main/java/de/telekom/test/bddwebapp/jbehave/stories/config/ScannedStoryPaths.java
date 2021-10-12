package de.telekom.test.bddwebapp.jbehave.stories.config;

import de.telekom.test.bddwebapp.jbehave.stories.AbstractStory;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.StoryPathResolver;
import org.junit.Ignore;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.ClassUtils.getDefaultClassLoader;
import static org.springframework.util.ClassUtils.resolveClassName;

/**
 * Scan story classes in classpath and add paths for execution
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface ScannedStoryPaths {

    default List<String> scannedStoryPaths() {
        var provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractStory.class));
        provider.addExcludeFilter(new AnnotationTypeFilter(Ignore.class));
        var components = provider.findCandidateComponents(storiesBasePath());
        StoryPathResolver storyPathResolver = configuration().storyPathResolver();
        return components.stream()
                .map(beanDefinition -> resolveClassName(requireNonNull(beanDefinition.getBeanClassName()), getDefaultClassLoader()))
                .map(aClass -> storyPathResolver.resolve((Class<? extends Embeddable>) aClass))
                .collect(toList());
    }

    default List<String> testLevelStoryPaths(int testLevel) {
        var provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractStory.class));
        provider.addExcludeFilter(new AnnotationTypeFilter(Ignore.class));
        var components = provider.findCandidateComponents(storiesBasePath());
        var storyPathResolver = configuration().storyPathResolver();
        return components.stream()
                .map(beanDefinition -> resolveClassName(requireNonNull(beanDefinition.getBeanClassName()), getDefaultClassLoader()))
                .filter(aClass -> {
                    de.telekom.test.bddwebapp.jbehave.stories.config.TestLevel atl = aClass.getAnnotation(TestLevel.class);
                    return (atl == null && testLevel == 0) ||
                            (atl != null && Arrays.stream(atl.testLevels()).anyMatch(tl -> tl == testLevel));
                })
                .map(aClass -> storyPathResolver.resolve((Class<? extends Embeddable>) aClass))
                .collect(toList());
    }

    /*
     * Override for better scan performance
     */
    default String storiesBasePath() {
        return "";
    }

    Configuration configuration();

}
