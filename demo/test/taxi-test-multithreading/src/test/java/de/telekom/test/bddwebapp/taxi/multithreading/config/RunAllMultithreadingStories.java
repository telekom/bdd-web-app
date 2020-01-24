package de.telekom.test.bddwebapp.taxi.multithreading.config;

import de.telekom.test.bddwebapp.stories.RunAllStories;
import de.telekom.test.bddwebapp.stories.customizing.CurrentStoryEmbedderMonitor;
import de.telekom.test.bddwebapp.taxi.config.ApplicationContextProvider;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.reporters.ReportsCount;
import org.springframework.context.ApplicationContext;

import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;

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
    public List<String> storyPaths() {
        String storyBasePath = "de/telekom/test/bddwebapp/taxi/multithreading/stories/login";
        List<String> storyPaths = new ArrayList<>();
        storyPaths.add(storyBasePath + "/login.story");
        for (int i = 2; i <= 100; i++) {
            storyPaths.add(storyBasePath + "/login" + i + ".story");
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
