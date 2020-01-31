package de.telekom.test.bddwebapp.taxi.multithreading.steps;

import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import de.telekom.test.bddwebapp.steps.Steps;
import org.jbehave.core.annotations.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static com.google.common.collect.ImmutableMap.of;

@Steps
public class MultithreadingAssertionSteps {

    @Autowired
    private StoryInteraction storyInteraction;

    @Given("broken user as user $user")
    public void brokenUser() {
        storyInteraction.rememberObject("user", of("username", "broken"));
    }

}
