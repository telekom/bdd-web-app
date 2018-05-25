package de.telekom.jbehave.webapp.interaction;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;

/**
 * Abstract class for test data management.
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public abstract class AbstractInteraction<T extends AbstractInteraction> {

    private final static String OBJECT_KEY_SEPARATOR = ".";
    private final static Pattern LIST_ATTRIBUTE = Pattern.compile("\\[\\d+\\]");

    protected HashMap<String, Object> context = Maps.newHashMap();

    public void startInteraction() {
        context = Maps.newHashMap();
    }

    /**
     * Store some data in the interaction context for later use.
     */
    public void remember(String key, Object value) {
        if (value instanceof Map) {
            ((Map) value).forEach((key1, value1) -> remember(key + OBJECT_KEY_SEPARATOR + key1.toString(), value1));
        }
        getContext().put(key, value);
    }

    /**
     * Store an object for an specific entity in the interaction context for later use. Recall this object with recallObject().
     */
    public void rememberObject(String entityKey, String objectKey, Object value) {
        String key = entityKey + OBJECT_KEY_SEPARATOR + objectKey;
        remember(key, value);
    }

    /**
     * Store an object for an specific entity in the interaction context for later use. Recall this object with recallObject().
     */
    public void rememberObject(String entityKey, Map<String, Object> object) {
        remember(entityKey, object);
    }

    public <S> void rememberToList(String key, S value) {
        List<Object> list = recallList(key);
        if (list == null) {
            list = new ArrayList<>();
            remember(key, list);
        }
        list.add(value);
    }

    /**
     * Get some data in the interaction context.
     */
    public <S> S recall(String key) {
        // get value from list
        Matcher matcher = LIST_ATTRIBUTE.matcher(key);
        if (matcher.find()) {
            return recallFromList(matcher);
        }

        return (S) getContext().get(key);
    }

    private <S> S recallFromList(Matcher matcher) {
        String item = matcher.group(0);
        Integer itemNo = Integer.valueOf(item.subSequence(1, item.length() - 1).toString());
        String attributeKey = matcher.replaceFirst("");
        List<S> list = recallList(attributeKey);
        return list.get(itemNo);
    }

    public <S> S recallNotNull(String key) {
        S value = recall(key);
        assertNotNull(String.format("Recalled '%s' for story interaction value '%s'", value, key), value);
        return value;
    }

    public <S> List<S> recallList(String key) {
        return recall(key);
    }

    /**
     * Get some data in the interaction context.
     */
    public <T> T recallObject(String objectKey, String attributeKey) {
        String key = objectKey + OBJECT_KEY_SEPARATOR + attributeKey;
        return recall(key);
    }

    /**
     * Get some data in the interaction context.
     */
    public <T> T recallObjectNotNull(String objectKey, String attributeKey) {
        String key = objectKey + OBJECT_KEY_SEPARATOR + attributeKey;
        return recallNotNull(key);
    }

    public HashMap<String, Object> getContext() {
        return context;
    }

}
