package de.telekom.test.bddwebapp.filemanipulator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Optional;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public class JBehaveStoryFile {

    private final File storyFile;
    private final Optional<File> storyClassFile;

}
