package de.telekom.test.bddwebapp.cucumber.hook;

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.*;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class InteractionLifeCycle  {

    @Autowired
    protected ScenarioInteraction scenarioInteraction;
    @Autowired
    protected StoryInteraction storyInteraction;

    @Before(order = BEFORE_FEATURE_ORDER)
    public void startStoryInteraction() {
        if (isBeforeFeature("startStoryInteraction")) {
            storyInteraction.startInteraction();
        }
    }

    @Before
    public void startScenarioInteraction() {
        scenarioInteraction.startInteraction();
        storyInteraction.setScenarioInteraction(scenarioInteraction);
        scenarioInteraction.setStoryInteraction(storyInteraction);
    }

}
