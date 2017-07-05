package de.telekom.config;

import de.telekom.test.stories.AbstractStory;
import org.springframework.context.ApplicationContext;

public class CollectiveTaxiStory extends AbstractStory {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

}
