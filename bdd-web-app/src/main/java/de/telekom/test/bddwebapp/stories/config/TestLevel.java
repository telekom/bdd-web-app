package de.telekom.test.bddwebapp.stories.config;

public @interface TestLevel {

    int[] testLevels() default 0;

}
