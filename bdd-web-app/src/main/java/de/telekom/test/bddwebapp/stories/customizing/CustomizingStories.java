package de.telekom.test.bddwebapp.stories.customizing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
public class CustomizingStories {

    private final Map<String, Class> storyClasses = new HashMap();

    @Getter
    @Setter
    private boolean apiOnlyForAllStories;

    public void setStoryClass(Class clazz, String name) {
        storyClasses.put(name, clazz);
    }

    public Class getStoryClass(String name) {
        return storyClasses.get(name);
    }

}
