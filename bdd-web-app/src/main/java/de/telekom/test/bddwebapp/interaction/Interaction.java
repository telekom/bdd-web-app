package de.telekom.test.bddwebapp.interaction;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Interface for Interactions
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
interface Interaction {

    /**
     * Store some data in the interaction context for later use.
     */
    void remember(String key, Object value);

    /**
     * Get some data in the interaction context.
     */
    default <S> S recall(String key) {
        return (S) getContext().get(key);
    }

    default <S> S recallNotNull(String key) {
        S value = recall(key);
        assertNotNull(String.format("Recalled '%s' for story interaction value '%s'", value, key), value);
        return value;
    }

    Map<String, Object> getContext();

}
