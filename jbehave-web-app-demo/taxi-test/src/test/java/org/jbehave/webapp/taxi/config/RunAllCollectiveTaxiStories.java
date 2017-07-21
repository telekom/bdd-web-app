package org.jbehave.webapp.taxi.config;

import org.jbehave.webapp.stories.RunAllStories;
import org.springframework.context.ApplicationContext;

public class RunAllCollectiveTaxiStories extends RunAllStories {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

}
