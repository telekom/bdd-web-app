package de.telekom.test.bddwebapp.jbehave.stories.config.scannedstorypathstest;

import org.jbehave.core.ConfigurableEmbedder;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class DummyReportingRunner extends Runner {

    public DummyReportingRunner(Class<? extends ConfigurableEmbedder> testClass) {
    }

    @Override
    public Description getDescription() {
        return Description.createSuiteDescription("Dummy");
    }

    @Override
    public void run(RunNotifier notifier) {
    }
}