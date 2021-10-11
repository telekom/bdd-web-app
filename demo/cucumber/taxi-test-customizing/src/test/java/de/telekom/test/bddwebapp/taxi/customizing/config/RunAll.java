package de.telekom.test.bddwebapp.taxi.customizing.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/de/telekom/test/bddwebapp/taxi/customizing/stories"},
        glue = {"de.telekom.test.bddwebapp.taxi.steps", "de.telekom.test.bddwebapp.taxi.customizing.steps"},
        tags = "not @ignore")
public class RunAll {
}
