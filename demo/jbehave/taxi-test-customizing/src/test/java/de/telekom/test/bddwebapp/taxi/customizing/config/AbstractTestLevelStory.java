package de.telekom.test.bddwebapp.taxi.customizing.config;

import de.telekom.test.bddwebapp.jbehave.stories.AbstractStory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.springframework.context.ApplicationContext;

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