package de.telekom.jbehave.webapp.taxi.config;

import de.telekom.jbehave.webapp.stories.AbstractStory;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.springframework.context.ApplicationContext;

import java.util.Locale;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public abstract class AbstractTaxiStory extends AbstractStory {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public Configuration configuration() {
        // use german keywords
        Locale de = new Locale("de");
        Keywords keywords = new LocalizedKeywords(de);
        Configuration configuration = defaultConfiguration();
        configuration.useKeywords(keywords);
        configuration.useStepCollector(new MarkUnmatchedStepsAsPending(keywords));
        configuration.useStoryReporterBuilder(defaultStoryReporterBuilder().withKeywords(keywords));
        configuration.useStoryParser(new RegexStoryParser(configuration));
        return configuration;
    }

}
