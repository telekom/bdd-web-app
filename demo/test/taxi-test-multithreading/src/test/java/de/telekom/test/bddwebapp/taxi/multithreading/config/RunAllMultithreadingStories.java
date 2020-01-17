package de.telekom.test.bddwebapp.taxi.multithreading.config;

import de.telekom.test.bddwebapp.stories.RunAllStories;
import de.telekom.test.bddwebapp.stories.customizing.CurrentStoryEmbedderMonitor;
import de.telekom.test.bddwebapp.taxi.config.ApplicationContextProvider;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.reporters.ReportsCount;
import org.springframework.context.ApplicationContext;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
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
    public String storiesBasePath() {
        return "de.telekom.test.bddwebapp.taxi.multithreading.stories";
    }

    @Override
    public Embedder configuredEmbedder() {
        Embedder embedder = super.configuredEmbedder();
        embedder.useEmbedderMonitor(new CurrentStoryEmbedderMonitor(getApplicationContext()));
        embedder.embedderControls().useThreads(2);
        return embedder;
    }

    @Override
    public List<String> storyPaths() {
        String story = super.storyPaths().get(0);
        // run story 10 times to test multithreading behaviour
        List<String> storyPathsMultiple = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            storyPathsMultiple.add(story);
        }
        return storyPathsMultiple;
    }
}
