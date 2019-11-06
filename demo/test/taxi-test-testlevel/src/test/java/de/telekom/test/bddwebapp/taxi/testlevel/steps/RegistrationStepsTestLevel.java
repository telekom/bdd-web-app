package de.telekom.test.bddwebapp.taxi.testlevel.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.steps.RegistrationSteps;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;

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
        if (Integer.parseInt(System.getProperty("testLevel")) == 0) {
            fail("This step should run only in test level 1 and above");
        }
        log.info("Create registered user in \"real-system\"");
        // In the real scenario you wouldn't run the super method.
        // You would use an own implementation that create the user in the real system.
        super.registeredUser(testobject);
    }

}
