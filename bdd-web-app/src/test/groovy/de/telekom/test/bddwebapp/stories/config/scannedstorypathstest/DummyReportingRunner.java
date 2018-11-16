package de.telekom.test.bddwebapp.stories.config.scannedstorypathstest;

import org.jbehave.core.ConfigurableEmbedder;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

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