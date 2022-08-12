package de.telekom.test.bddwebapp.gherkinconverter;

import de.telekom.test.bddwebapp.filemanipulator.JBehaveStoryFile;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
public class JBehaveGherkinConverter {

    private final List<JBehaveStoryFile> jBehaveStoryFiles;

    private final Translator translator = new Translator();

    public JBehaveGherkinConverter(List<JBehaveStoryFile> jBehaveStoryFiles) {
        this.jBehaveStoryFiles = jBehaveStoryFiles;
    }

    public List<FeatureWrapper> convertJBehaveToGherkin() throws IOException {
        log.info("Convert stories to gherkin.");

        var cucumberFeatures = new LinkedList<FeatureWrapper>();
        for (var jBehaveStoryFile : jBehaveStoryFiles) {
            log.debug("Convert story {} to gherkin.", jBehaveStoryFile.getStoryFile().getName());

            var storyFileReader = new FileReader(jBehaveStoryFile.getStoryFile());
            var jBehaveReader = new JBehaveReader(storyFileReader);
            var story = jBehaveReader.read();
            var feature = translator.translate(story);
            feature.setFile(createFeatureFile(jBehaveStoryFile));

            if (jBehaveStoryFile.getStoryClassFile().isPresent()) {
                addStoryAnnotationsToGherkin(feature, jBehaveStoryFile.getStoryClassFile().get());
            }

            cucumberFeatures.add(feature);
        }
        return cucumberFeatures;
    }

    private File createFeatureFile(JBehaveStoryFile jBehaveStoryFile) {
        return new File(jBehaveStoryFile.getStoryFile().getParent(), jBehaveStoryFile.getStoryFile().getName().replaceFirst(".story", ".feature"));
    }

    public void addStoryAnnotationsToGherkin(FeatureWrapper feature, File storyClassFile) throws IOException {
        Scanner scanner = new Scanner(storyClassFile);
        if (scanner.findAll("@RestartBrowserBeforeScenario").findAny().isPresent()) {
            log.debug("Found annotation @RestartBrowserBeforeScenario in \"{}\". Add tag to feature.", storyClassFile.getName());
            // TODO
          //  feature.getFeature().getTags().add(new Tag("restartBrowserBeforeScenario", feature.getFeature().getTags().size() + 1));
        }
    }

}
