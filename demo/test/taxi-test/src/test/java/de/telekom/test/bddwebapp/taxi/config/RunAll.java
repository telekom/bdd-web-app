package de.telekom.test.bddwebapp.taxi.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:"},
        plugin = {"pretty"},
        glue = {"de.telekom.test.bddwebapp"})
@SpringJUnitWebConfig
@ComponentScan("de.telekom.test.bddwebapp")
public class RunAll {
}
