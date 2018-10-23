package de.telekom.test.bddwebapp.stories.customizing;

import de.telekom.test.bddwebapp.api.ApiOnly;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

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
    private final StoryClasses storyClasses;

    @Getter
    @Setter
    private String fullStoryPath;

    public String getStoryName() {
        return fullStoryPath.substring(fullStoryPath.lastIndexOf("/") + 1, fullStoryPath.lastIndexOf("."));
    }

    public Class getStoryClass() {
        return storyClasses.getStoryClass(getStoryName());
    }

    public boolean isApiOnly() {
        Class clazz = getStoryClass();
        if (clazz == null) {
            return false;
        }
        return asList(clazz.getAnnotations()).stream()
                .anyMatch(annotation -> annotation.annotationType().equals(ApiOnly.class));
    }

}
