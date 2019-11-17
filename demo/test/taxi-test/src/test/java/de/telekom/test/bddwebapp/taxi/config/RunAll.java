package de.telekom.test.bddwebapp.taxi.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:"},
        plugin = {"pretty"},
        glue = {"de.telekom.test.bddwebapp.taxi"})
public class RunAll {
}
