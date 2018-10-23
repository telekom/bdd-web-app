package de.telekom.test.bddwebapp.stories.customizing;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jbehave.core.Embeddable;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.springframework.context.ApplicationContext;

/**
 * This Story Path Resolver save the story class in spring context to use story customisations, e.g. @ApiOnly.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RequiredArgsConstructor
public class CustomizingStoryPathResolver extends UnderscoredCamelCaseResolver {

    @NonNull
    private final ApplicationContext applicationContext;

    public CustomizingStories getStoryClasses() {
        return applicationContext.getBean(CustomizingStories.class);
    }

    protected String resolveName(Class<? extends Embeddable> embeddableClass) {
        String path = super.resolveName(embeddableClass);
        getStoryClasses().setStoryClass(embeddableClass, path);
        return path;
    }

}