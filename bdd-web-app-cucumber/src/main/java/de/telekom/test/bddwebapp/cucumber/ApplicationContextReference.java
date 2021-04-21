package de.telekom.test.bddwebapp.cucumber;

import org.springframework.context.ApplicationContext;

public class ApplicationContextReference {

    private static ApplicationContext applicationContext;

    private ApplicationContextReference() {
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextReference.applicationContext = applicationContext;
    }
}
