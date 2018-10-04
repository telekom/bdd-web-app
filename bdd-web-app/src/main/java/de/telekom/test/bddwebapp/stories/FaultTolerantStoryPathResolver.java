package de.telekom.test.bddwebapp.stories;

import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.springframework.context.ApplicationContext;

/**
 * Story path resolver for most common configuration faults
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface FaultTolerantStoryPathResolver {

    default StoryPathResolver removeStoryFromClassNameStoryPathResolver() {
        return new UnderscoredCamelCaseResolver().removeFromClassName("Story");
    }

    ApplicationContext getApplicationContext();


}
