package de.telekom.test.bddwebapp.taxi.customizing.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/de/telekom/test/bddwebapp/taxi/customizing/stories"},
        glue = {"de.telekom.test.bddwebapp.taxi.steps", "de.telekom.test.bddwebapp.taxi.customizing.steps"},
        tags = "not @ignore")
public class RunAll {
}
