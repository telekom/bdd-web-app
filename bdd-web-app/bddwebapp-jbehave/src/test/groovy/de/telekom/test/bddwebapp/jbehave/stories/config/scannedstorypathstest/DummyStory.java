package de.telekom.test.bddwebapp.jbehave.stories.config.scannedstorypathstest;

import de.telekom.test.bddwebapp.jbehave.stories.AbstractStory;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RunWith(DummyReportingRunner.class)
public abstract class DummyStory extends AbstractStory {

    public ApplicationContext getApplicationContext() {
        return null;
    }

    public Configuration configuration() {
        return new MostUsefulConfiguration();
    }

    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new ArrayList<>());
    }

    public Embedder configuredEmbedder() {
        return new Embedder() {
            public void runStoriesAsPaths(List<String> storyPaths) {
            }

            public void runAsEmbeddables(List<String> classNames) {
            }

            public void runStoriesWithAnnotatedEmbedderRunner(List<String> classNames) {
            }
        };
    }

    public void run() {
    }

}
