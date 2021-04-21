package de.telekom.test.bddwebapp.stories.customizing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Arrays.stream;

/**
 * Gives access to the current story about the Spring Context
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Marc Eckart - Initial implementation of current story bean
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class CurrentFeature {

    @Autowired
    private CustomizingStories customizingStories;

    @Getter
    @Setter
    private String feature;

    public boolean isRestartBrowserBeforeScenario() {
        return isRestartBrowserBeforeScenarioForAllStories() ||
                isRestartBrowserBeforeScenarioForCurrentStory();
    }

    private boolean isRestartBrowserBeforeScenarioForCurrentStory() {
        return customizingStories.getRestartBrowserBeforeScenarioThisFeatures().stream().anyMatch(s -> s.equals(feature));
    }

    private boolean isRestartBrowserBeforeScenarioForAllStories() {
        return customizingStories.isRestartBrowserBeforeScenarioForAllStories();
    }

}
