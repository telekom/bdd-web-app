package de.telekom.test.bddwebapp.steps;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

/**
 * Steps factory for automatic step instantiation by class path scan
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Yasin Yildiz {@literal <YildizY@telekom.de>} - Coauthor of the optimized test level steps factory
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface ScannedStepsFactory {

    default InjectableStepsFactory scannedStepsFactory() {
        List<Object> steps = new ArrayList(getApplicationContext().getBeansWithAnnotation(Steps.class).values());
        return new InstanceStepsFactory(configuration(), steps);
    }

    default InjectableStepsFactory testLevelStepsFactory(int testLevel) {
        List<Object> selectedSteps = new ArrayList<>();
        List<Object> stepsMatchingTestLevel = getApplicationContext().getBeansWithAnnotation(Steps.class).values().stream()
                .filter(step -> step.getClass().getAnnotation(Steps.class).testLevel() <= testLevel)
                .collect(Collectors.toList());
        stepsMatchingTestLevel.forEach(currentStep -> stepsMatchingTestLevel.stream()
                .filter(step -> currentStep.getClass().isAssignableFrom(step.getClass()) || step.getClass().isAssignableFrom(currentStep.getClass()))
                .max(comparingInt(step -> step.getClass().getAnnotation(Steps.class).testLevel()))
                .ifPresent(selectedSteps::add));
        return new InstanceStepsFactory(configuration(), selectedSteps);
    }

    default int getTestLevel() {
        Integer testLevel = getApplicationContext().getEnvironment().getProperty("testLevel", Integer.class);
        if (testLevel == null) {
            return 0;
        }
        return testLevel;
    }

    ApplicationContext getApplicationContext();

    Configuration configuration();

}
