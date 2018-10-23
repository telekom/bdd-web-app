package de.telekom.test.bddwebapp.frontend.lifecycle;

import de.telekom.test.bddwebapp.stories.customizing.CurrentStory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Maintains the lifecycle of the WebDriver.
 * The TestRule guarantees that before the JBehave lifecycle begins a WebDriver instance exists.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SeleniumTestRule implements MethodRule {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @NonNull
    private final CurrentStory currentStory;

    @NonNull
    private final WebDriverWrapper webDriverWrapper;

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                if (currentStory.isApiOnly()) {
                    statement.evaluate();
                } else {
                    startBrowser();
                    statement.evaluate();
                    stopBrowser();
                }
            }

            private void startBrowser() {
                log.info("Start Testcase");
                try {
                    if (webDriverWrapper.isClosed()) {
                        webDriverWrapper.loadWebdriver();
                    }
                } catch (Exception e) {
                    log.warn("can not start the browser " + e.getMessage());
                }
            }

            private void stopBrowser() {
                log.info("Testcase finished");
                try {
                    webDriverWrapper.quit();
                } catch (Exception e) {
                    log.warn("can not stop the browser " + e.getMessage());
                }
            }
        };
    }

}
