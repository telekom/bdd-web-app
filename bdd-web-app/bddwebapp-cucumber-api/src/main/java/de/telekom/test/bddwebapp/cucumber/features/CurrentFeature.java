package de.telekom.test.bddwebapp.cucumber.features;

import io.cucumber.java.Scenario;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Gives access to the current story about the Spring Context
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Marc Eckart - Initial implementation of current story bean
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class CurrentFeature {

    @Getter
    private String currentFeature;

    @Getter
    private Integer testCaseCountCurrentFeature = 0;

    private final CustomizingFeatures customizingFeatures;

    public CurrentFeature(CustomizingFeatures customizingFeatures) {
        this.customizingFeatures = customizingFeatures;
    }

    public boolean isRestartBrowserBeforeScenario() {
        return isRestartBrowserBeforeScenarioForAllStories() || isRestartBrowserBeforeScenarioForCurrentStory();
    }

    private boolean isRestartBrowserBeforeScenarioForCurrentStory() {
        return customizingFeatures.getRestartBrowserBeforeScenarioThisFeatures().stream().anyMatch(s -> s.equals(currentFeature));
    }

    private boolean isRestartBrowserBeforeScenarioForAllStories() {
        return customizingFeatures.isRestartBrowserBeforeScenarioForAllStories();
    }

    public boolean isBeforeFeature() {
        return testCaseCountCurrentFeature < 1;
    }

    public void beforeScenarioHook(Scenario scenario) {
        String featureNameFromScenario = getFeatureNameFromScenario(scenario);
        increaseTestCaseCount(featureNameFromScenario);
        handleCustomFeatureAnnotations(scenario);
    }

    public void increaseTestCaseCount(String feature) {
        if (feature.equals(currentFeature)) {
            testCaseCountCurrentFeature++;
        } else {
            log.info("Execution for new feature {} is started", feature);
            currentFeature = feature;
            testCaseCountCurrentFeature = 0;
        }
    }

    public void handleCustomFeatureAnnotations(Scenario scenario) {
        if (scenario.getSourceTagNames() != null && scenario.getSourceTagNames().stream().anyMatch(s -> s.equals("@restartBrowserBeforeScenario"))) {
            customizingFeatures.getRestartBrowserBeforeScenarioThisFeatures().add(currentFeature);
        }
    }

    public static String getFeatureNameFromScenario(Scenario scenario) {
        var uri = scenario.getUri().toString();
        var feature = uri.replaceFirst(".+/", "");
        feature = feature.replaceFirst(":.*", "");
        return feature;
    }
}
