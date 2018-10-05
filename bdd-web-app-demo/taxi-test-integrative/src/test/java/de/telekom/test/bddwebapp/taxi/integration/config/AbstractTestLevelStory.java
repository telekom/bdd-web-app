package de.telekom.test.bddwebapp.taxi.integration.config;

import de.telekom.test.bddwebapp.stories.AbstractStory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.springframework.context.ApplicationContext;

public class AbstractTestLevelStory extends AbstractStory {

    private final int testLevel = 1;

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return testLevelStepsFactory(testLevel);
    }

}