package de.telekom.test.bddwebapp.filemanipulator;

import de.telekom.test.bddwebapp.gherkinconverter.FeatureWrapper;
import de.telekom.test.bddwebapp.gherkinconverter.GherkinWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
@RequiredArgsConstructor
public class CucumberFeatureFileWriter {

    private final List<FeatureWrapper> cucumberFeatures;

    public void writeFeaturesToFile() throws IOException {
        log.debug("Write cucumber features.");

        for (var cucumberFeature : cucumberFeatures) {
            try (var fileWriterWithEncoding = new FileWriterWithEncoding(cucumberFeature.getFile(), Charset.defaultCharset())) {
                log.debug("Write cucumber feature file: {}.", cucumberFeature.getFile().getAbsolutePath());
                var gherkinWriter = new GherkinWriter(fileWriterWithEncoding);
                gherkinWriter.write(cucumberFeature);
            }
        }
    }

}
