package de.telekom.jbehave.webapp.interaction;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Save the data flat for the test execution.
 * <p>
 * If data is stored in this interaction, the values from maps and lists are stored recursively too.
 * The following examples illustrate this:
 * - remember("key", "value") = ["key", "value"]
 * - remember("key.key", "value") = ["key.key", "value"]
 * - remember("key", ["key": "value"]) = ["key": ["key": "value"], "key.key", "value"]
 * - remember("key", [["key": "value"]] = ["key": ["value"], "key[0].key", "value"]
 * Further examples can be found in @see FlatInteractionSpec.
 * <p>
 * The advantage of this approach is the safe and fast availability of the test data,
 * but be careful when saving large and deep maps as this approach increases the amount of data exponentially!
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 */
public abstract class FlatInteraction implements Interaction {

    private static String OBJECT_KEY_SEPARATOR = ".";
    private static String LIST_ITEM_FORMAT = "[%d]";

    @Getter
    protected Map<String, Object> context = Maps.newHashMap();

    public void startInteraction() {
        context = Maps.newHashMap();
    }

    /**
     * Store some test data in the interaction context for later use.
     * Converts maps and list so that they are flat.
     * Also saves the maps and lists as individual objects.
     * Be careful when saving large and deep maps as this approach increases the amount of data exponentially!
     */
    public void remember(String key, Object value) {
        if (value instanceof Map) {
            ((Map) value).forEach((entryKey, entryValue) -> remember(key + OBJECT_KEY_SEPARATOR + entryKey.toString(), entryValue));
        }
        if (value instanceof List) {
            List list = ((List) value);
            for (int i = 0; i < list.size(); i++) {
                remember(key + String.format(LIST_ITEM_FORMAT, i), list.get(i));
            }
        }
        getContext().put(key, value);
    }

    // -------------------------------------------------------------------------
    // Object Handling
    // -------------------------------------------------------------------------

    public void rememberObject(String entityKey, String objectKey, Object value) {
        String key = entityKey + OBJECT_KEY_SEPARATOR + objectKey;
        remember(key, value);
    }

    public void rememberObject(String entityKey, Map<String, Object> object) {
        remember(entityKey, object);
    }

    public <S> S recallObject(String objectKey, String attributeKey) {
        String key = objectKey + OBJECT_KEY_SEPARATOR + attributeKey;
        return recall(key);
    }

    public <S> S recallObjectNotNull(String objectKey, String attributeKey) {
        String key = objectKey + OBJECT_KEY_SEPARATOR + attributeKey;
        return recallNotNull(key);
    }

    public <S> Map<String, S> recallMap(String key) {
        return recall(key);
    }

    // -------------------------------------------------------------------------
    // List Handling
    // -------------------------------------------------------------------------

    public <S> void rememberToList(String key, S value) {
        List<Object> list = recallList(key);
        if (list == null) {
            list = new ArrayList<>();
            remember(key, list);
        }
        list.add(value);
    }

    public <S> List<S> recallList(String key) {
        return recall(key);
    }

}
