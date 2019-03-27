package de.telekom.test.bddwebapp.interaction.steps;

import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.steps.Steps;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.ScenarioType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InteractionLifecycleSteps {

    @NonNull
    protected final ScenarioInteraction scenarioInteraction;
    @NonNull
    protected final StoryInteraction storyInteraction;

    @BeforeStory
    public void beforeStory() {
        storyInteraction.startInteraction();
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
