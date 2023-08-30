package de.telekom.test.bddwebapp.gherkinconverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jbehave.core.model.Story;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@AllArgsConstructor
@Data
public class StoryWrapper {

    private final String storyAsText;

    private final Story story;

}
