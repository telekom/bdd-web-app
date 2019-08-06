package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.steps.RegistrationSteps;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.annotations.Given;

import static org.junit.Assert.fail;


/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps(testLevel = 1)
@Slf4j
public class RegistrationStepsTestLevel extends RegistrationSteps {

    @Override
    @Given("registered user as $testobject")
    public void registeredUser(String testobject) {
        if (Integer.valueOf(System.getProperty("testLevel")) == 0) {
            fail("This step should run only in test level 1 and above");
        }
        log.info("Create registered user in \"real-system\"");
        // In the real scenario you wouldn't run the super method.
        // You would use an own implementation that create the user in the real system.
        super.registeredUser(testobject);
    }

}
