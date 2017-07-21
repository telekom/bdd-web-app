package org.jbehave.webapp.taxi.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextProvider {

	private static final ApplicationContext applicationContext = createApplicationContext();

	private static AnnotationConfigApplicationContext createApplicationContext() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(TestConfig.class);
		applicationContext.refresh();
		return applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
