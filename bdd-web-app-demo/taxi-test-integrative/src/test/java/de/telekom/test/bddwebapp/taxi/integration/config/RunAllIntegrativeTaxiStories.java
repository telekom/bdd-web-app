package de.telekom.test.bddwebapp.taxi.integration.config;

import de.telekom.test.bddwebapp.stories.RunAllStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class RunAllIntegrativeTaxiStories extends RunAllStories {

    private final int testLevel = 1;

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return testLevelStepsFactory(testLevel);
    }

    @Override
    public List<String> storyPaths() {
        return testLevelStoryPaths(testLevel);
    }

    /*
     * Empty base path for test level selection testing
     */
    @Override
    public String storiesBasePath() {
        return "";
    }

}
