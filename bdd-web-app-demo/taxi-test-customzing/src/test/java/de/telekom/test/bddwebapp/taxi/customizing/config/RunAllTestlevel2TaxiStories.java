package de.telekom.test.bddwebapp.taxi.customizing.config;

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
public class RunAllTestlevel2TaxiStories extends RunAllStories {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProviderApiOnly.getApplicationContext();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return testLevelStepsFactory(2);
    }

    @Override
    public List<String> storyPaths() {
        return testLevelStoryPaths(2);
    }

    /*
     * Empty base path for test level selection testing
     */
    @Override
    public String storiesBasePath() {
        return "de.telekom.test.bddwebapp.taxi.customizing";
    }

}
