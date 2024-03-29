package de.telekom.test.bddwebapp.gherkinconverter;

import gherkin.formatter.Formatter;
import gherkin.formatter.PrettyFormatter;

import java.io.Writer;

/**
 * Based on <a href="https://github.com/seize-the-dave/jbehave-to-gherkin">...</a>.
 */
public class GherkinWriter {
    private final Writer writer;

    public GherkinWriter(Writer writer) {
        this.writer = writer;
    }

    public void write(FeatureWrapper gherkin) {
        Formatter formatter = new PrettyFormatter(writer, true, false);
        gherkin.replay(formatter);
    }
}