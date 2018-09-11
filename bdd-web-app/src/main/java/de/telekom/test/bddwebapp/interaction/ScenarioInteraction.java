package de.telekom.test.bddwebapp.interaction;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.telekom.test.bddwebapp.api.RequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Holds context variables that are needed in the entire JBehave Scenario.
 * <p/>
 * An Interaction is a spring bean which keeps ThreadLocal state information of a single specification execution available across different steps. The
 * Interaction is setup and torn down before and after every specification execution.
 * <p>
 * After a first implementation of scenario interaction by Sven Schomaker, working for Deutsche Telekom AG in 2013.
 * <p>
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Component
public class ScenarioInteraction extends FlatInteraction {

    private static final String BODY = "BODY";
    private static final String PATH_PARAMS = "PATH_PARAMS";
    private static final String QUERY_PARAMS = "QUERY_PARAMS";

    private StoryInteraction storyInteraction;

    @Autowired
    private RequestBuilder requestBuilder;

    /**
     * Initializes this interaction with an Array JSON body.
     */
    public List<Object> arrayBody() {
        Object body = recall(BODY);
        if (body == null) {
            remember(BODY, Lists.newArrayList());
        }
        return (List<Object>) recallNotNull(BODY);
    }

    @Override
    public void startInteraction() {
        super.startInteraction();
        requestBuilder.clearRequest();
    }

    public Map<String, Object> mapBody() {
        return getMapFromStoryInteraction(BODY);
    }

    public Map<String, String> mapPathParam() {
        return getMapFromStoryInteraction(PATH_PARAMS);
    }

    public Map<String, String> mapQueryParam() {
        return getMapFromStoryInteraction(QUERY_PARAMS);
    }

    public <T> Map<String, T> getMapFromStoryInteraction(String queryParams) {
        Object body = recall(queryParams);
        if (body == null) {
            remember(queryParams, Maps.newHashMap());
        }
        return (Map<String, T>) recallNotNull(queryParams);
    }

    /**
     * Store some data from story interaction to the scenario interaction context
     */
    public void rememberFromStoryInteraction(String key) {
        super.remember(key, storyInteraction.recallNotNull(key));
    }

    /**
     * Store an object from story interaction for an specific entity in the scenario interaction context. Recall this object with recallObject().
     */
    public void rememberObjectFromStoryInteraction(String entityKey, String objectKey) {
        super.rememberObject(entityKey, objectKey, storyInteraction.recallObjectNotNull(entityKey, objectKey));
    }

    public void setStoryInteraction(StoryInteraction storyInteraction) {
        this.storyInteraction = storyInteraction;
    }

}
