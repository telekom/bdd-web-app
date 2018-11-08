package de.telekom.test.bddwebapp.steps;

import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

/**
 * Maps interaction key like $key to value. Simple values will return as value.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoryInteractionParameterConverter {

    @NonNull
    private final StoryInteraction storyInteraction;

    public String getValue(String keyOrValueOrConcatenated) {
        if (isConcatenatedKey(keyOrValueOrConcatenated)) {
            return concatenatedKey(keyOrValueOrConcatenated);
        }
        return mapToValue(keyOrValueOrConcatenated);
    }

    protected boolean isKey(String keyOrValueOrConcatenated) {
        return keyOrValueOrConcatenated.startsWith("$");
    }

    protected boolean isConcatenatedKey(String keyOrValueOrConcatenated) {
        return keyOrValueOrConcatenated.contains("$") && keyOrValueOrConcatenated.contains("+");
    }

    protected String concatenatedKey(String concatenatedKey) {
        return of(concatenatedKey.split("\\+"))
                .map(this::mapToValue)
                .collect(joining());
    }

    protected String mapToValue(String keyOrValueOrConcatenated) {
        if (isKey(keyOrValueOrConcatenated)) {
            return getStoryInteractionValue(keyOrValueOrConcatenated.substring(1));
        }
        return keyOrValueOrConcatenated;
    }

    protected String getStoryInteractionValue(String key) {
        String value = storyInteraction.recallNotNull(key).toString();
        // get list values as comma separated list, e.g. [value] is value or [value1,value2] is value1,value2
        if (value.startsWith("[") && value.endsWith("]")) {
            value = value.substring(1, value.length() - 1);
        }
        return value;
    }

}
