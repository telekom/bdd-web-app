package de.telekom.test.bddwebapp.stories;

import de.telekom.test.bddwebapp.steps.ScannedStepsFactory;
import de.telekom.test.bddwebapp.stories.config.FaultTolerantStoryPathResolver;
import de.telekom.test.bddwebapp.stories.config.ScreenshotStoryReporterBuilder;
import de.telekom.test.bddwebapp.stories.config.StoryRunner;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.springframework.context.ApplicationContext;

/**
 * Basis story
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@StoryRunner
public abstract class AbstractStory extends JUnitStory implements ScannedStepsFactory, ScreenshotStoryReporterBuilder, FaultTolerantStoryPathResolver {

    @Override
    public Configuration configuration() {
        Configuration configuration = new MostUsefulConfiguration();
        configuration.useStoryReporterBuilder(screenshotStoryReporterBuilder());
        configuration.useStoryPathResolver(removeStoryFromClassNameStoryPathResolver());
        return configuration;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return scannedStepsFactory();
    }

    public abstract ApplicationContext getApplicationContext();

}
