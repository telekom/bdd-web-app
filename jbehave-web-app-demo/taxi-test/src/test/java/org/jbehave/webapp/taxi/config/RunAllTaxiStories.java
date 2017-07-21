package org.jbehave.webapp.taxi.config;

import org.jbehave.webapp.stories.RunAllStories;
import org.springframework.context.ApplicationContext;

public class RunAllTaxiStories extends RunAllStories {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public String storiesBasePath() {
        return "org.jbehave.webapp.taxi.stories";
    }

}
