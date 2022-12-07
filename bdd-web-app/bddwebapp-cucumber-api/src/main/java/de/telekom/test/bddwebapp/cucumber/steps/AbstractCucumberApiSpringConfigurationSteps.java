package de.telekom.test.bddwebapp.cucumber.steps;

import de.telekom.test.bddwebapp.api.steps.ApiSteps;
import de.telekom.test.bddwebapp.cucumber.features.CurrentFeature;
import de.telekom.test.bddwebapp.cucumber.features.CustomizingFeatures;
import de.telekom.test.bddwebapp.interaction.InteractionParameterConverter;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
public abstract class AbstractCucumberApiSpringConfigurationSteps extends ApiSteps {

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected ScenarioInteraction scenarioInteraction;

    @Autowired
    protected StoryInteraction storyInteraction;

    @Autowired
    protected InteractionParameterConverter interactionParameterConverter;

    @Autowired
    protected CustomizingFeatures customizingFeatures;

    @Autowired
    protected CurrentFeature currentFeature;

    protected boolean beforeAll = true;

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    public void setupBddWebApp(Scenario scenario) {
        startScenarioInteraction(scenario);
        currentFeature.beforeScenarioHook(scenario);
        startStoryInteraction(scenario);
    }

    public void startScenarioInteraction(Scenario scenario) {
        log.info("Reset scenario interaction");
        scenarioInteraction.startInteraction();
    }

    public void startStoryInteraction(Scenario scenario) {
        if (currentFeature.isBeforeFeature()) {
            log.info("Reset story interaction");
            storyInteraction.startInteraction();
            storyInteraction.remember(StoryInteraction.BDDWEBAPP_VARIANT, "cucumber");
        }
    }

    public String resolveInteractionKey(String interactionKey) {
        return interactionParameterConverter.getValueFromKeyOrValueOrConcatenated(interactionKey);
    }

}
