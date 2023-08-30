package de.telekom.test.bddwebapp.interaction;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Holds context variables that are needed in the entire JBehave Scenario.
 * <p>
 * An Interaction is a spring bean which keeps ThreadLocal state information of a single specification execution available across different steps. The
 * Interaction is setup and torn down before and after every specification execution.
 * <p>
 * After a first implementation of scenario interaction by Sven Schomaker, working for Deutsche Telekom AG in 2013.
 * <p>
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class ScenarioInteraction extends FlatInteraction {

    public static final String BODY = "BODY";
    public static final String PATH_PARAMS = "PATH_PARAMS";
    public static final String QUERY_PARAMS = "QUERY_PARAMS";

    @Setter
    private StoryInteraction storyInteraction;

    @Override
    public void startInteraction() {
        super.startInteraction();
    }

    public List<Object> arrayBody() {
        return recallListOrCreateNew(BODY);
    }

    public Map<String, Object> mapBody() {
        return recallMapOrCreateNew(BODY);
    }

    public Map<String, String> mapPathParam() {
        return recallMapOrCreateNew(PATH_PARAMS);
    }

    public Map<String, String> mapQueryParam() {
        return recallMapOrCreateNew(QUERY_PARAMS);
    }

    /**
     * Store some data from story interaction to the scenario interaction context
     */
    public void rememberFromStoryInteraction(String key) {
        remember(key, storyInteraction.recallNotNull(key));
    }

    /**
     * Store an object from story interaction for an specific entity in the scenario interaction context. Recall this object with recallObject().
     */
    public void rememberObjectFromStoryInteraction(String entityKey, String objectKey) {
        rememberObject(entityKey, objectKey, storyInteraction.recallObjectNotNull(entityKey, objectKey));
    }

}
