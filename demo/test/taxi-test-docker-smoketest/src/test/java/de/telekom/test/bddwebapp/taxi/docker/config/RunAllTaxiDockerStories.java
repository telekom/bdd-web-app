package de.telekom.test.bddwebapp.taxi.docker.config;

import de.telekom.test.bddwebapp.stories.RunAllStories;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class RunAllTaxiDockerStories extends RunAllStories {

    public static void main(String[] args) {
        System.setProperty("browser", "htmlunit");

        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        Result result = junit.run(RunAllTaxiDockerStories.class);
        if (!result.wasSuccessful()) {
            System.exit(1);
        }
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
