package de.telekom.test.bddwebapp.taxi.customizing.config.runall;

import de.telekom.test.bddwebapp.stories.RunAllStories;
import de.telekom.test.bddwebapp.taxi.customizing.config.ApplicationContextProvider;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class RunAllTestlevel1TaxiStories extends RunAllStories {

    @Override
    public Configuration configuration() {
        System.setProperty("testLevel", "1");
        return super.configuration();
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return testLevelStepsFactory(getTestLevel());
    }

    @Override
    public List<String> storyPaths() {
        return testLevelStoryPaths(getTestLevel());
    }

    /*
     * Empty base path for test level selection testing
     */
    @Override
    public String storiesBasePath() {
        return "de.telekom.test.bddwebapp.taxi.customizing";
    }

}
