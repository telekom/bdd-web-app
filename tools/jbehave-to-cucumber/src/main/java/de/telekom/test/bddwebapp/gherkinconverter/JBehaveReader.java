package de.telekom.test.bddwebapp.gherkinconverter;

import org.apache.commons.io.IOUtils;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.RegexStoryParser;

import java.io.IOException;
import java.io.Reader;

/**
 * Based on https://github.com/seize-the-dave/jbehave-to-gherkin.
 */
public class JBehaveReader {
    private final Reader reader;

    public JBehaveReader(Reader reader) {
        this.reader = reader;
    }

    public Story read() throws IOException {
        RegexStoryParser storyParser = new RegexStoryParser();
        return storyParser.parseStory(IOUtils.toString(reader));
    }
}