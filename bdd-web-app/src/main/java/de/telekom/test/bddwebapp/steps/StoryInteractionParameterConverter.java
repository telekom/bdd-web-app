package de.telekom.test.bddwebapp.steps;

import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

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
@Steps
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoryInteractionParameterConverter {

    public static String KEY_LITERAL = "$";
    public static String CONCATENATED_LITERAL = "+";

    @NonNull
    private final StoryInteraction storyInteraction;

    @AsParameterConverter
    public String getValueFromKeyOrValueOrConcatenated(String keyOrValueOrConcatenated) {
        if (isConcatenatedKey(keyOrValueOrConcatenated)) {
            return concatenatedKey(keyOrValueOrConcatenated);
        }
        return mapToValue(keyOrValueOrConcatenated);
    }

    public List<Map<String, String>> getRowsWithInteractionKey(ExamplesTable examplesTable) {
        List<Map<String, String>> rows = examplesTable.getRows();
        rows.forEach(map -> map.entrySet().forEach(entry -> entry.setValue(getValueFromKeyOrValueOrConcatenated(entry.getValue()))));
        return rows;
    }

    protected boolean isKey(String keyOrValueOrConcatenated) {
        return keyOrValueOrConcatenated.startsWith(KEY_LITERAL);
    }

    protected boolean isConcatenatedKey(String keyOrValueOrConcatenated) {
        return keyOrValueOrConcatenated.contains(KEY_LITERAL) && keyOrValueOrConcatenated.contains(CONCATENATED_LITERAL);
    }

    protected String concatenatedKey(String concatenatedKey) {
        return of(concatenatedKey.split("\\" + CONCATENATED_LITERAL))
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
