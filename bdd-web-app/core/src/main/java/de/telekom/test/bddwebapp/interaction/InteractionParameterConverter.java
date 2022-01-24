package de.telekom.test.bddwebapp.interaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

/**
 * Maps interaction key like $key to value. Simple values will return as value.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Slf4j
public class InteractionParameterConverter {

    public static String KEY_LITERAL = "$";
    public static String CONCATENATED_LITERAL = "+";

    @Autowired
    private ScenarioInteraction scenarioInteraction;

    @Autowired
    private StoryInteraction storyInteraction;

    public <S> S getValueFromKeyOrValueOrConcatenated(String keyOrValueOrConcatenated) {
        if (isConcatenatedKey(keyOrValueOrConcatenated)) {
            return (S) concatenatedKey(keyOrValueOrConcatenated);
        }
        return mapToValue(keyOrValueOrConcatenated);
    }

    protected boolean isKey(String keyOrValueOrConcatenated) {
        return keyOrValueOrConcatenated.startsWith(KEY_LITERAL);
    }

    protected boolean isConcatenatedKey(String keyOrValueOrConcatenated) {
        return keyOrValueOrConcatenated.contains(KEY_LITERAL) && keyOrValueOrConcatenated.contains(CONCATENATED_LITERAL);
    }

    protected String concatenatedKey(String concatenatedKey) {
        return of(concatenatedKey.split("\\" + CONCATENATED_LITERAL))
                .map(keyOrValueOrConcatenated -> (String) mapToValue(keyOrValueOrConcatenated))
                .collect(joining());
    }

    protected <S> S mapToValue(String keyOrValueOrConcatenated) {
        if (isKey(keyOrValueOrConcatenated)) {
            return getInteractionValue(keyOrValueOrConcatenated.substring(1));
        }
        return (S) keyOrValueOrConcatenated;
    }

    protected <S> S getInteractionValue(String key) {
        S value = storyInteraction.recall(key);
        if (value == null) {
            value = scenarioInteraction.recallNotNull(key);
            log.info("Don't find key {} in story interaction but in scenario interaction with value {}", key, value);
        }
        // get list values as comma separated list, e.g. [value] is value or [value1,value2] is value1,value2
        if (value instanceof String && ((String) value).startsWith("[") && ((String) value).endsWith("]")) {
            value = (S) ((String) value).substring(1, ((String) value).length() - 1);
        }
        return value;
    }

}
