package de.telekom.test.bddwebapp.taxi.integration.steps;

import de.telekom.test.bddwebapp.api.RequestBuilder;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.steps.Steps;
import de.telekom.test.bddwebapp.taxi.steps.RegistrationSteps;
import org.jbehave.core.annotations.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Steps(testLevel = 1)
public class RegistrationStepsIntegration extends RegistrationSteps {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${hostIncludingPort}")
    private String hostIncludingPort;

    @Autowired
    private StoryInteraction storyInteraction;

    @Autowired
    private RequestBuilder requestBuilder;

    @Override
    @Given("registered user as $testobject")
    public void registeredUser(String testobject) {
        logger.info("Create registered user in real system");
        requestBuilder.requestWithJsonConfig(hostIncludingPort, "testData/user", "", "").post();
        storyInteraction.rememberObject(testobject, requestBuilder.getResponseAsMap());
    }

}
