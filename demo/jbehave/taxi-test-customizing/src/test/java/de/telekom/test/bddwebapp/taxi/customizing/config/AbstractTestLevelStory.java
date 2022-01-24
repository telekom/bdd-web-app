package de.telekom.test.bddwebapp.taxi.customizing.config;

import de.telekom.test.bddwebapp.jbehave.stories.AbstractStory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public abstract class AbstractTestLevelStory extends AbstractStory {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return testLevelStepsFactory(getTestLevel());
    }

}