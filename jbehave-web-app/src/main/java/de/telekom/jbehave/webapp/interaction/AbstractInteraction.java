package de.telekom.jbehave.webapp.interaction;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    protected final HashMap<String, Object> context = Maps.newHashMap();
    private final ThreadLocal<T> threadLocal = new ThreadLocal<>();

    public void startInteraction() {
        try {
            setInteraction((T) this.getClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setInteraction(T interaction) {
        threadLocal.set(interaction);
    }

    public void stopInteraction() {
        threadLocal.remove();
    }

    /**
     * Store some data in the interaction context for later use.
     */
    public void remember(String key, Object value) {
        threadLocal.get().context.put(key, value);
    }

    /**
     * Store an object for an specific entity in the interaction context for later use. Recall this object with recallObject().
     */
    public void rememberObject(String entityKey, String objectKey, Object value) {
        String key = entityKey + OBJECT_KEY_SEPARATOR + objectKey;
        remember(key, value);
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
        return (S) threadLocal.get().getContext().get(key);
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
