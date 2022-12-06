package de.telekom.test.bddwebapp;

import de.telekom.test.bddwebapp.filemanipulator.*;
import de.telekom.test.bddwebapp.gherkinconverter.FeatureWrapper;
import de.telekom.test.bddwebapp.gherkinconverter.JBehaveGherkinConverter;
import de.telekom.test.bddwebapp.stepconverter.JBehaveCucumberStepsConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
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
public class MigrationTool {

    private File basePath;
    private List<JBehaveStoryFile> jBehaveStoryFiles;
    private List<File> jBehaveSteps;
    private List<FeatureWrapper> cucumberFeatures;

    public static void main(String[] args) throws IOException {
        var migrationTool = new MigrationTool();
        migrationTool.enterBasePath();
        migrationTool.scanStoryFiles();
        migrationTool.scanSteps();
        migrationTool.convertStoriesToGherkin();
        migrationTool.convertStepsToCucumber();
        migrationTool.writeFeatures();
        migrationTool.deleteStoryFiles();
    }

    private void enterBasePath() {
        var scanner = new Scanner(System.in);
        System.out.println("Please enter JBehave base directory:");
        var file = new File(scanner.nextLine());
        while (!file.isDirectory()) {
            log.warn("Given path is not a directory! Please enter again:");
            file = new File(scanner.nextLine());
        }
        basePath = file;
    }

    private void scanStoryFiles() {
        var jBehaveStoryFileScanner = new JBehaveStoryFileScanner(basePath);
        jBehaveStoryFiles = jBehaveStoryFileScanner.searchForJBehaveStories();
    }

    private void scanSteps() {
        var jBehaveStepsScanner = new JBehaveStepsScanner(basePath);
        jBehaveSteps = jBehaveStepsScanner.searchForJBehaveSteps();
    }

    private void convertStoriesToGherkin() throws IOException {
        var jBehaveGherkinConverter = new JBehaveGherkinConverter(jBehaveStoryFiles);
        cucumberFeatures = jBehaveGherkinConverter.convertJBehaveToGherkin();
    }

    private void convertStepsToCucumber() throws IOException {
        var jBehaveCucumberStepsConverter = new JBehaveCucumberStepsConverter(jBehaveSteps);
        jBehaveCucumberStepsConverter.convertToCucumberSteps();
    }

    private void writeFeatures() throws IOException {
        var cucumberFeatureFileWriter = new CucumberFeatureFileWriter(cucumberFeatures);
        cucumberFeatureFileWriter.writeFeaturesToFile();
    }

    private void deleteStoryFiles() {
        var jBehaveStoryFileDeletion = new JBehaveStoryFileDeletion(jBehaveStoryFiles);
        jBehaveStoryFileDeletion.deleteJBehaveStories();
    }

}