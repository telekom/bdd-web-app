package de.telekom.test.bddwebapp.taxi.docker.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.Properties;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class ApplicationContextProvider {

    private static AnnotationConfigApplicationContext applicationContext;

    public static AnnotationConfigApplicationContext createApplicationContext(String[] args) {
        System.out.println(args);

        applicationContext = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Properties props = new Properties();
        props.put("spring.datasource.url", "<my value>");
        environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
        applicationContext.register(TestConfig.class);
        applicationContext.refresh();
        return applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
