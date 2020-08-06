package de.telekom.test.bddwebapp.taxi.stories.login;

import de.telekom.test.bddwebapp.frontend.steps.WebDriverLifecycleSteps;
import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.config.AbstractTaxiStory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class Login extends AbstractTaxiStory {

    public InjectableStepsFactory scannedStepsFactory() {
        return new InstanceStepsFactory(configuration(), getApplicationContext().getBeansWithAnnotation(Steps.class).values().stream()
                .filter(step -> !step.getClass().equals(WebDriverLifecycleSteps.class))
                .collect(Collectors.toList()));
    }

}
