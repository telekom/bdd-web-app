package de.telekom.test.bddwebapp.cucumber.extension;

import cucumber.api.event.*;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import org.springframework.context.ApplicationContext;

import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.increaseTestCaseCountForBeforeAll;
import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.increaseTestCaseCountForFeature;

public class ExtendedLifeCyclePlugin implements ConcurrentEventListener {

    private EventHandler<TestCaseStarted> testCaseStarted = event -> {
        increaseTestCaseCountForBeforeAll();
        increaseTestCaseCountForFeature(event.testCase.getUri());
    };

    private EventHandler<TestRunFinished> testRunFinished = event -> {
        ApplicationContext applicationContext = ApplicationContextReference.getApplicationContext();
        WebDriverWrapper webDriverWrapper = applicationContext.getBean(WebDriverWrapper.class);
        webDriverWrapper.quit();
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, testCaseStarted);
        publisher.registerHandlerFor(TestRunFinished.class, testRunFinished);
    }

}