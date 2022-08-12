package de.telekom.test.bddwebapp.filemanipulator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
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
public class JBehaveStoryFileDeletion {

    private final List<JBehaveStoryFile> jBehaveStoryFiles;

    public void deleteJBehaveStories() {
        log.info("Delete JBehave stories and corresponding classes");
        for (var jBehaveStoryFile : jBehaveStoryFiles) {
            jBehaveStoryFile.getStoryFile().delete();
            jBehaveStoryFile.getStoryClassFile().ifPresent(File::delete);
        }
    }

}
