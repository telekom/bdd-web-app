package de.telekom.test.bddwebapp.steps;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Steps factory for automatic step instantiation by class path scan
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
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
        Collection<Object> allSteps = getApplicationContext().getBeansWithAnnotation(Steps.class).values();
        allSteps.forEach(step -> {
            int stepTestLevel = step.getClass().getAnnotation(Steps.class).testLevel();
            if (testLevel >= stepTestLevel) {
                selectedSteps.add(step);
                // remove parents with lower test level
                selectedSteps.removeAll(selectedSteps.stream()
                        .filter(selectedStep -> step.getClass().isAssignableFrom(selectedStep.getClass()) || selectedStep.getClass().isAssignableFrom(step.getClass()))
                        .filter(selectedStep -> selectedStep.getClass().getAnnotation(Steps.class).testLevel() < step.getClass().getAnnotation(Steps.class).testLevel())
                        .collect(toList()));
            }
        });
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
