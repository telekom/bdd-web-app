package de.telekom.test.bddwebapp.taxi.customizing.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class ApplicationContextProvider {

    private static final ApplicationContext applicationContext = createApplicationContext();

    private static AnnotationConfigApplicationContext createApplicationContext() {
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TestConfig.class);
        applicationContext.refresh();
        return applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
