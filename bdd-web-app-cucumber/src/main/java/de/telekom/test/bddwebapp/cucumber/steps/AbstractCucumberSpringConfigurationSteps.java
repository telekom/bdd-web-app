package de.telekom.test.bddwebapp.cucumber.steps;

import ch.qos.logback.classic.Level;
import de.telekom.test.bddwebapp.api.steps.ApiSteps;
import de.telekom.test.bddwebapp.cucumber.features.CurrentFeature;
import de.telekom.test.bddwebapp.cucumber.features.CustomizingFeatures;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.interaction.InteractionParameterConverter;
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
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom AG
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
    protected WebDriverLifeCycle webDriverLifeCycle;

    @Autowired
    protected CustomizingFeatures customizingStories;

    @Autowired
    protected CurrentFeature currentFeature;

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    public void setupBddWebApp(Scenario scenario) {
        setupApplicationContext();
        increaseTestCaseCountForBeforeAll();

        startScenarioInteraction();

        String featureName = getFeatureName(scenario);
        increaseTestCaseCountForFeature(featureName);
        startStoryInteraction();

        setLogLevel();

        currentFeature.setFeature(featureName);
        if (scenario.getSourceTagNames() != null && scenario.getSourceTagNames().stream().anyMatch(s -> s.equals("@restartBrowserBeforeScenario"))) {
            customizingStories.getRestartBrowserBeforeScenarioThisFeatures().add(featureName);
        }
    }

    public void afterFeature(Scenario scenario) {
        webDriverLifeCycle.quitBrowserAfterScenario();
    }

    public String getFeatureName(Scenario scenario) {
        String id = scenario.getId();
        String feature = id.replaceFirst(".+/", "");
        feature = feature.replaceFirst(":.*", "");
        return feature;
    }

    public void setupApplicationContext() {
        if (isBeforeAll()) {
            log.info("Setup application");
            setApplicationContext(applicationContext);

            webDriverLifeCycle.updateDriver();
        }
    }

    public void startScenarioInteraction() {
        log.info("Reset scenario interaction");
        scenarioInteraction.startInteraction();
    }

    public void startStoryInteraction() {
        if (isBeforeFeature()) {
            log.info("Reset story interaction");
            storyInteraction.startInteraction();

            webDriverLifeCycle.quitBrowserAfterStory();
        }
    }

    public String resolveInteractionKey(String interactionKey) {
        return interactionParameterConverter.getValueFromKeyOrValueOrConcatenated(interactionKey);
    }

    public void setLogLevel() {
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
    }

}
