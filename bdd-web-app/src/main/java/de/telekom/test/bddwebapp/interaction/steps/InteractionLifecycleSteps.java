package de.telekom.test.bddwebapp.interaction.steps;

import de.telekom.test.bddwebapp.cucumber.BeforeFeature;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class InteractionLifecycleSteps {

    @Autowired
    protected ScenarioInteraction scenarioInteraction;
    @Autowired
    protected StoryInteraction storyInteraction;

    @BeforeFeature
    public void beforeStory() {
        storyInteraction.startInteraction();
    }

    @Before
    public void beforeScenario() {
        scenarioInteraction.startInteraction();
        storyInteraction.setScenarioInteraction(scenarioInteraction);
        scenarioInteraction.setStoryInteraction(storyInteraction);
    }

}
