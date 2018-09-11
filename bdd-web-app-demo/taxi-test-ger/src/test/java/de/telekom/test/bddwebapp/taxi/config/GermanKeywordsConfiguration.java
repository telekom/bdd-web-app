package de.telekom.test.bddwebapp.taxi.config;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;

import java.util.Locale;

public interface GermanKeywordsConfiguration {

    default Configuration germanKeywordsConfiguration() {
        Locale de = new Locale("de");
        Keywords keywords = new LocalizedKeywords(de);
        Configuration configuration = defaultConfiguration();
        configuration.useKeywords(keywords);
        configuration.useStepCollector(new MarkUnmatchedStepsAsPending(keywords));
        configuration.useStoryReporterBuilder(defaultStoryReporterBuilder().withKeywords(keywords));
        configuration.useStoryParser(new RegexStoryParser(configuration));
        return configuration;
    }

    StoryReporterBuilder defaultStoryReporterBuilder();

    Configuration defaultConfiguration();

}
