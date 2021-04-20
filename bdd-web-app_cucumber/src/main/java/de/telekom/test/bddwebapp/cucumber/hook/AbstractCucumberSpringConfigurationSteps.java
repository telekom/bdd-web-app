package de.telekom.test.bddwebapp.cucumber.hook;

import ch.qos.logback.classic.Level;
import de.telekom.test.bddwebapp.api.steps.ApiSteps;
import de.telekom.test.bddwebapp.cucumber.WebDriverLifeCycle;
import de.telekom.test.bddwebapp.interaction.ScenarioInteraction;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.steps.InteractionParameterConverter;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PreDestroy;

import static de.telekom.test.bddwebapp.cucumber.ApplicationContextReference.setApplicationContext;
import static de.telekom.test.bddwebapp.cucumber.ExtendedLifeCycle.*;

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

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    public void setupBddWebApp(Scenario scenario) {
        setupApplicationContext();
        increaseTestCaseCountForBeforeAll();

        startScenarioInteraction();

        increaseTestCaseCountForFeature(getFeatureName(scenario));
        startStoryInteraction();

        setLogLevel();
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
