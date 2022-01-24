package de.telekom.test.bddwebapp.taxi.docker.config;

import de.telekom.test.bddwebapp.jbehave.stories.RunAllStories;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class RunAllTaxiDockerStories extends RunAllStories {

    public static void main(String[] args) {
        ApplicationContextProvider.createApplicationContext(args);
        var junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        var result = junit.run(RunAllTaxiDockerStories.class);
        System.exit(result.wasSuccessful() ? 0 : 1);
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public String storiesBasePath() {
        return "de.telekom.test.bddwebapp.taxi.docker.stories";
    }

}
