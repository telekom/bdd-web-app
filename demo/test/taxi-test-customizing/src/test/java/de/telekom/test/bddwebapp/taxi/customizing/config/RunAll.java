package de.telekom.test.bddwebapp.taxi.customizing.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:"},
        plugin = {"de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCyclePlugin"},
        glue = {"de.telekom.test.bddwebapp.taxi.steps", "de.telekom.test.bddwebapp.cucumber.hook"},
        tags = "not @ignore")
public class RunAll {
}
