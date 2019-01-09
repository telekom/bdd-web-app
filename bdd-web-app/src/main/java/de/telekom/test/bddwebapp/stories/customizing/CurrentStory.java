package de.telekom.test.bddwebapp.stories.customizing;

import de.telekom.test.bddwebapp.api.ApiOnly;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Arrays.stream;

/**
 * Gives access to the current story about the Spring Context
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Marc Eckart
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrentStory {

    @NonNull
    private final CustomizingStories customizingStories;

    @Getter
    @Setter
    private String storyPath;

    public String getStoryName() {
        return storyPath.substring(storyPath.lastIndexOf("/") + 1, storyPath.lastIndexOf("."));
    }

    public Class getStoryClass() {
        return customizingStories.getStoryClass(getStoryName());
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

}
