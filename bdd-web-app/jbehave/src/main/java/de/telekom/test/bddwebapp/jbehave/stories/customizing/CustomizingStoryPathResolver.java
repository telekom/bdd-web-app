package de.telekom.test.bddwebapp.jbehave.stories.customizing;

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
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RequiredArgsConstructor
public class CustomizingStoryPathResolver extends UnderscoredCamelCaseResolver {

    @NonNull
    protected final ApplicationContext applicationContext;

    public de.telekom.test.bddwebapp.jbehave.stories.customizing.CustomizingStories getStoryClasses() {
        return applicationContext.getBean(CustomizingStories.class);
    }

    protected String resolveName(Class<? extends Embeddable> embeddableClass) {
        var path = super.resolveName(embeddableClass);
        getStoryClasses().setStoryClass(embeddableClass, path);
        return path;
    }

}