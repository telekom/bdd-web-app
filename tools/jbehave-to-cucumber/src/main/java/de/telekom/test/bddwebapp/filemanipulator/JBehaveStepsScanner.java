package de.telekom.test.bddwebapp.filemanipulator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
@RequiredArgsConstructor
public class JBehaveStepsScanner {

    private final File basePath;

    private List<File> jBehaveStepsFiles;

    public List<File> searchForJBehaveSteps() {
        log.info("Search for JBehave Step classes.");
        jBehaveStepsFiles = new LinkedList<>();
        _searchForJBehaveSteps(basePath);
        log.info("Found {} JBehave steps.", jBehaveStepsFiles.size());
        return jBehaveStepsFiles;
    }

    private void _searchForJBehaveSteps(File directory) {
        var files = directory.listFiles();
        if (files != null) {
            for (var file : files) {
                if (file.isDirectory()) {
                    _searchForJBehaveSteps(file);
                } else if (file.getName().endsWith("Steps.java")) {
                    log.debug("Found step class: {}", file.getName());
                    jBehaveStepsFiles.add(file);
                }
            }
        }
    }

}
