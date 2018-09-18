package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.steps.Steps;
import org.jbehave.core.annotations.BeforeScenario;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 */
@Steps
public class CommonSteps {

    @Autowired
    private StoryInteraction storyInteraction;

    @BeforeScenario
    public void randomNumber() {
        storyInteraction.remember("RANDOM", randomNumeric(8));
    }

}
