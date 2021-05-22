package de.telekom.test.bddwebapp.taxi.multithreading.config;

import de.telekom.test.bddwebapp.jbehave.stories.RunAllStories;
import de.telekom.test.bddwebapp.jbehave.stories.customizing.CurrentStoryEmbedderMonitor;
import de.telekom.test.bddwebapp.taxi.config.ApplicationContextProvider;
import org.jbehave.core.embedder.Embedder;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class RunAllMultithreadingStories extends RunAllStories {

    @Override
    public ApplicationContext getApplicationContext() {
        System.setProperty("browser", "htmlunit");
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public List<String> storyPaths() {
        var storyPaths = new ArrayList<String>();
        storyPaths.addAll(multiplyStories("login"));
        storyPaths.addAll(multiplyStories("registration"));
        shuffle(storyPaths);
        return storyPaths;
    }

    public List<String> multiplyStories(String story) {
        var storyBasePath = "de/telekom/test/bddwebapp/taxi/multithreading/stories/";
        var storyPaths = new ArrayList<String>();
        storyPaths.add(storyBasePath + story + ".story");
        for (int i = 2; i <= 49; i++) {
            storyPaths.add(storyBasePath + story + i + ".story");
        }
        return storyPaths;
    }

    @Override
    public Embedder configuredEmbedder() {
        Embedder embedder = super.configuredEmbedder();
        embedder.useEmbedderMonitor(new CurrentStoryEmbedderMonitor(getApplicationContext()));
        embedder.embedderControls().useThreads(4).ignoreFailureInStories();
        return embedder;
    }

}
