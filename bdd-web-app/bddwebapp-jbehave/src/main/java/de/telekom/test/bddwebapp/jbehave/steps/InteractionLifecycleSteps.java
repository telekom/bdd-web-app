package de.telekom.test.bddwebapp.jbehave.steps;

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.ScenarioType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
public class InteractionLifecycleSteps {

    protected final ScenarioInteraction scenarioInteraction;
    protected final StoryInteraction storyInteraction;

    @Autowired
    public InteractionLifecycleSteps(ScenarioInteraction scenarioInteraction, StoryInteraction storyInteraction) {
        this.scenarioInteraction = scenarioInteraction;
        this.storyInteraction = storyInteraction;
    }

    @BeforeStory
    public void beforeStory() {
        storyInteraction.startInteraction();
        storyInteraction.remember(StoryInteraction.BDDWEBAPP_VARIANT, "jbehave");
    }

    @BeforeScenario(uponType = ScenarioType.NORMAL)
    public void beforeScenarioForNormal() {
        beforeScenario(ScenarioType.NORMAL);
    }

    @BeforeScenario(uponType = ScenarioType.EXAMPLE)
    public void beforeScenarioForExample() {
        beforeScenario(ScenarioType.EXAMPLE);
    }

    protected void beforeScenario(ScenarioType type) {
        scenarioInteraction.startInteraction();
        storyInteraction.setScenarioInteraction(scenarioInteraction);
        scenarioInteraction.setStoryInteraction(storyInteraction);
    }

}
