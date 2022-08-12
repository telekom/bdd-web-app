package de.telekom.test.bddwebapp.filemanipulator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
@RequiredArgsConstructor
public class JBehaveStoryFileScanner {

    private final File basePath;

    private List<JBehaveStoryFile> jBehaveStoryFiles;

    public List<JBehaveStoryFile> searchForJBehaveStories() {
        log.info("Search for JBehave .story files and corresponding Java classes.");
        jBehaveStoryFiles = new LinkedList<>();
        _searchForJBehaveStories(basePath);
        log.info("Found {} JBehave stories.", jBehaveStoryFiles.size());
        return jBehaveStoryFiles;
    }

    private void _searchForJBehaveStories(File directory) {
        var files = directory.listFiles();
        if (files != null) {
            for (var file : files) {
                if (file.isDirectory()) {
                    _searchForJBehaveStories(file);
                } else if (file.getName().endsWith(".story") && !file.getAbsolutePath().contains("target")) {
                    var jBehaveStoryFile = new JBehaveStoryFile(file, findStoryClassFile(file));
                    log.info("Found story: {}. Found class: {}", jBehaveStoryFile.getStoryFile().getName(), jBehaveStoryFile.getStoryClassFile().map(File::getName).orElse("NO CLASS FOUND!"));
                    jBehaveStoryFiles.add(jBehaveStoryFile);
                }
            }
        }
    }

    private Optional<File> findStoryClassFile(File storyFile) {
        var storyClassDirectory = new File(storyFile.getParentFile().getAbsolutePath().replaceFirst("resources", "java"));
        if (storyClassDirectory.isDirectory()) {
            var files = storyClassDirectory.listFiles();
            if (files != null) {
                return stream(files)
                        .filter(file -> isCorrespondingJBehaveStoryClass(storyFile, file))
                        .findFirst();
            }
        }
        log.warn("No story class found for story: {}. Searched in directory {}.", storyFile.getName(), storyClassDirectory.getAbsolutePath());
        return Optional.empty();
    }

    private boolean isCorrespondingJBehaveStoryClass(File storyFile, File file) {
        var classNameIgnoreCase = file.getName()
                .replaceFirst("\\.java", "")
                .replaceFirst("Story$", "");
        return storyFile.getName()
                .replaceAll("_", "")
                .replaceFirst("\\.story", "")
                .equalsIgnoreCase(classNameIgnoreCase);
    }

}



