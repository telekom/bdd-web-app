package de.telekom.test.bddwebapp.stories.customizing;

import lombok.RequiredArgsConstructor;
import org.jbehave.core.embedder.PrintStreamEmbedderMonitor;
import org.springframework.context.ApplicationContext;

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
@RequiredArgsConstructor
public class CurrentStoryEmbedderMonitor extends PrintStreamEmbedderMonitor {

    private final ApplicationContext applicationContext;

    @Override
    public void runningStory(String path) {
        CurrentStory currentStory = applicationContext.getBean(CurrentStory.class);
        currentStory.setFullStoryPath(path);
        super.runningStory(path);
    }

}
