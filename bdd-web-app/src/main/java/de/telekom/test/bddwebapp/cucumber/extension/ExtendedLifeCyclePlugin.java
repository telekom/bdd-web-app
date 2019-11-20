package de.telekom.test.bddwebapp.cucumber.extension;

import cucumber.api.event.*;

import java.util.HashSet;
import java.util.Set;

public class ExtendedLifeCyclePlugin implements ConcurrentEventListener {

    private static Set<String> allFeaturesStarted = new HashSet<>();
    private static Set<String> allFeaturesFinished = new HashSet<>();

    private EventHandler<TestCaseStarted> testCaseStarted = event -> {
        ExtendedLifeCycle.resetBeforeAll();
        if (!allFeaturesStarted.contains(event.testCase.getUri())) {
            ExtendedLifeCycle.resetBeforeFeature();
            allFeaturesStarted.add(event.testCase.getUri());
        }
    };

    private EventHandler<TestCaseFinished> testCaseFinished = event -> {
        if (!allFeaturesFinished.contains(event.testCase.getUri())) {
            ExtendedLifeCycle.resetAfterFeature();
            allFeaturesFinished.add(event.testCase.getUri());
        }
    };

    private EventHandler<TestRunFinished> testRunFinished = event -> {
        // TODO quit webdriver
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, testCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, testCaseFinished);
        publisher.registerHandlerFor(TestRunFinished.class, testRunFinished);
    }

}