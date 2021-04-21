package de.telekom.test.bddwebapp.taxi.ger.config;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;

import java.util.Locale;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class GermanKeywordsConfiguration{

    public static Configuration germanKeywordsConfiguration() {
        var de = new Locale("de");
        var keywords = new LocalizedKeywords(de);
        var configuration = new MostUsefulConfiguration();
        configuration.useKeywords(keywords);
        configuration.useStepCollector(new MarkUnmatchedStepsAsPending(keywords));
        configuration.useStoryParser(new RegexStoryParser(configuration));
        configuration.useStoryPathResolver(new UnderscoredCamelCaseResolver(".spezifikation").removeFromClassName("Story"));
        return configuration;
    }

}
