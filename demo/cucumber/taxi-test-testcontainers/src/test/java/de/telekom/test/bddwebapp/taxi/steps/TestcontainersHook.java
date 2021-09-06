package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.taxi.config.TestdriverWebDriverConfiguration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.lifecycle.TestDescription;

import java.util.Optional;

public class TestcontainersHook {

    @Autowired
    private TestdriverWebDriverConfiguration webDriverConfig;

    @Before
    public void beforeScenario() {
        webDriverConfig.getContainer().withExposedPorts(5000, 5001).start();
    }

    @After
    public void afterScenario(Scenario scenario) {
        webDriverConfig.getContainer().afterTest(new TestDescription() {
            @Override
            public String getTestId() {
                return scenario.getId();
            }
            @Override
            public String getFilesystemFriendlyName() {
                return scenario.getName();
            }
        }, Optional.of(scenario).filter(Scenario::isFailed).map(__ -> new RuntimeException()));
    }

}