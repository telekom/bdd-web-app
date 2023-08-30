package de.telekom.test.bddwebapp.jbehave.stories.config;

import de.telekom.test.bddwebapp.jbehave.stories.customizing.CustomizingStoryPathResolver;
import org.jbehave.core.io.StoryPathResolver;
import org.springframework.context.ApplicationContext;

/**
 * Story path resolver for most common configuration faults
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface FaultTolerantStoryPathResolver {

    default StoryPathResolver removeStoryFromClassNameStoryPathResolver() {
        return new CustomizingStoryPathResolver(getApplicationContext()).removeFromClassName("Story");
    }

    ApplicationContext getApplicationContext();

}
