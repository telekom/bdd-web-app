package de.telekom.test.bddwebapp.cucumber.extension;

import org.springframework.context.ApplicationContext;

public class ApplicationContextReference {

    private ApplicationContextReference() {
    }

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextReference.applicationContext = applicationContext;
    }
}
