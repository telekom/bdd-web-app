package org.jbehave.webapp.taxi.config;

import org.jbehave.webapp.stories.AbstractStory;
import org.springframework.context.ApplicationContext;

public abstract class AbstractTaxiStory extends AbstractStory {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

}
