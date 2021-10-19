package de.telekom.test.bddwebapp.cucumber.steps;

import ch.qos.logback.classic.Level;
import de.telekom.test.bddwebapp.api.steps.ApiSteps;
import de.telekom.test.bddwebapp.cucumber.features.CurrentFeature;
import de.telekom.test.bddwebapp.cucumber.features.CustomizingFeatures;
import de.telekom.test.bddwebapp.interaction.InteractionParameterConverter;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static de.telekom.test.bddwebapp.cucumber.steps.ApplicationContextReference.setApplicationContext;
import static de.telekom.test.bddwebapp.cucumber.steps.ExtendedLifeCycle.*;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
public abstract class AbstractCucumberSpringConfigurationSteps extends ApiSteps {

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected ScenarioInteraction scenarioInteraction;

    @Autowired
    protected StoryInteraction storyInteraction;

    @Autowired
    protected InteractionParameterConverter interactionParameterConverter;

    @Autowired
    protected CustomizingFeatures customizingStories;

    @Autowired
    protected CurrentFeature currentFeature;

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    public void setupBddWebApp(Scenario scenario) {
        basicSetup(scenario);
    }

    public void basicSetup(Scenario scenario) {
        setupApplicationContext();
        setLogLevel();

        increaseTestCaseCountForBeforeAll();
        startScenarioInteraction();
        handleFeature(scenario);
        startStoryInteraction();
    }

    public void handleFeature(Scenario scenario) {
        String featureName = getFeatureNameFromScenario(scenario);
        increaseTestCaseCountForFeature(featureName);
        setFeatureNameToCurrentSpringContext(featureName);
        handleCustomFeatureAnnotations(featureName, scenario);
    }

    public void handleCustomFeatureAnnotations(String featureName, Scenario scenario) {
        if (scenario.getSourceTagNames() != null && scenario.getSourceTagNames().stream().anyMatch(s -> s.equals("@restartBrowserBeforeScenario"))) {
            customizingStories.getRestartBrowserBeforeScenarioThisFeatures().add(featureName);
        }
    }

    public void setupApplicationContext() {
        if (isBeforeAll()) {
            log.info("Setup application");
            setApplicationContext(applicationContext);
        }
    }

    public void setLogLevel() {
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
    }

    public String getFeatureNameFromScenario(Scenario scenario) {
        String uri = scenario.getUri().toString();
        String feature = uri.replaceFirst(".+/", "");
        feature = feature.replaceFirst(":.*", "");
        return feature;
    }

    public void setFeatureNameToCurrentSpringContext(String featureName) {
        currentFeature.setFeature(featureName);
    }

    public void startScenarioInteraction() {
        log.info("Reset scenario interaction");
        scenarioInteraction.startInteraction();
    }

    public void startStoryInteraction() {
        if (isBeforeFeature()) {
            log.info("Reset story interaction");
            storyInteraction.startInteraction();
        }
    }

    public String resolveInteractionKey(String interactionKey) {
        return interactionParameterConverter.getValueFromKeyOrValueOrConcatenated(interactionKey);
    }

}
