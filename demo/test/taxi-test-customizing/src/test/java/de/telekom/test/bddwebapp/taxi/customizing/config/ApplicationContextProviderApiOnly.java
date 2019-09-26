package de.telekom.test.bddwebapp.taxi.customizing.config;

import de.telekom.test.bddwebapp.stories.customizing.CustomizingStories;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class ApplicationContextProviderApiOnly {

    private static final ApplicationContext applicationContext = createApplicationContext();

    private static AnnotationConfigApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TestConfig.class);
        applicationContext.refresh();
        CustomizingStories storyClasses = applicationContext.getBean(CustomizingStories.class);
        storyClasses.setApiOnlyForAllStories(true);
        return applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
