package de.telekom.test.bddwebapp.interaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Interface for Interactions
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
interface Interaction {

    String OBJECT_KEY_SEPARATOR = ".";

    /**
     * Store some data in the interaction context for later use.
     */
    void remember(String key, Object value);

    /**
     * Store some data in the interaction context for later use. Use enum as key.
     */
    default void remember(Enum key, Object value) {
        remember(key.toString(), value);
    }

    /**
     * Get some data in the interaction context.
     */
    default <S> S recall(String key) {
        return (S) getContext().get(key);
    }

    /**
     * Get some data in the interaction context. Use enum as key.
     */
    default <S> S recall(Enum key) {
        return recall(key.toString());
    }

    default <S> S recall(String key, Class<S> type) {
        return type.cast(recall(key));
    }

    default <S> S recall(Enum key, Class<S> type) {
        return recall(key.toString(), type);
    }

    default <S> S recallNotNull(String key) {
        S value = recall(key);
        assertNotNull(String.format("Recalled '%s' for story interaction value '%s'", value, key), value);
        return value;
    }

    default <S> S recallNotNull(Enum key) {
        return recallNotNull(key.toString());
    }

    default <S> S recallNotNull(String key, Class<S> type) {
        return type.cast(recallNotNull(key));
    }

    default <S> S recallNotNull(Enum key, Class<S> type) {
        return type.cast(recallNotNull(key.toString()));
    }

    Map<String, Object> getContext();

    // -------------------------------------------------------------------------
    // Object Handling
    // -------------------------------------------------------------------------

    default void rememberObject(String entityKey, String objectKey, Object value) {
        Object object = recall(entityKey);

        Map<String, Object> objectMap;
        if (object instanceof Map) {
            objectMap = (Map<String, Object>) object;
        } else {
            objectMap = new HashMap<>();
        }
        objectMap.put(objectKey, value);

        remember(entityKey, objectMap);

        /* This is a fallback for the special case that the key with a simple value already exists in interaction.
         * E.g. if you remember("key", "value") and than remember("key", "object", "value").
         * Try to avoid this construct as it leads to inconsistencies! */
        if (object != null && !(object instanceof Map)) {
            remember(entityKey, object);
        }
    }

    default void rememberObject(Enum entityKey, String objectKey, Object value) {
        rememberObject(entityKey.toString(), objectKey, value);
    }

    default void rememberObject(String entityKey, Map<String, Object> object) {
        remember(entityKey, object);
    }

    default <S> S recallObject(String objectKey, String attributeKey) {
        String key = objectKey + OBJECT_KEY_SEPARATOR + attributeKey;
        return recall(key);
    }

    default <S> S recallObjectNotNull(String objectKey, String attributeKey) {
        String key = objectKey + OBJECT_KEY_SEPARATOR + attributeKey;
        return recallNotNull(key);
    }

    default <S> S recallObject(Enum objectKey, String attributeKey) {
        return recallObject(objectKey.toString(), attributeKey);
    }

    default <S> S recallObjectNotNull(Enum objectKey, String attributeKey) {
        return recallObjectNotNull(objectKey.toString(), attributeKey);
    }

    default <S> Map<String, S> recallMap(String key) {
        return recall(key);
    }

    default <S> Map<String, S> recallMap(Enum key) {
        return recall(key.toString());
    }

    // -------------------------------------------------------------------------
    // List Handling
    // -------------------------------------------------------------------------

    default <S> void rememberToList(String key, S value) {
        List<Object> list = recallList(key);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(value);
        remember(key, list);
    }

    default <S> void rememberToList(Enum key, S value) {
        rememberToList(key.toString(), value);
    }

    default <S> List<S> recallList(String key) {
        return recall(key);
    }

    default <S> List<S> recallList(Enum key) {
        return recall(key);
    }

}
