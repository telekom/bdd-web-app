package de.telekom.test.bddwebapp.cucumber.extension;

import cucumber.api.event.*;

import java.util.HashSet;
import java.util.Set;

import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.increaseTestCaseCountForBeforeAll;
import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.increaseTestCaseCountForFeature;

public class ExtendedLifeCyclePlugin implements ConcurrentEventListener {

    private EventHandler<TestCaseStarted> testCaseStarted = event -> {
        increaseTestCaseCountForBeforeAll();
        increaseTestCaseCountForFeature(event.testCase.getUri());
    };

    private EventHandler<TestRunFinished> testRunFinished = event -> {
        PluginWebDriverReference.getWebDriverWrapper().quit();
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, testCaseStarted);
        publisher.registerHandlerFor(TestRunFinished.class, testRunFinished);
    }

}