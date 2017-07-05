package de.telekom.config;

import de.telekom.test.stories.RunAllStories;
import org.springframework.context.ApplicationContext;

public class RunAllCollectiveTaxiStories extends RunAllStories {

	@Override
	public ApplicationContext getApplicationContext() {
		return ApplicationContextProvider.getApplicationContext();
	}

}
