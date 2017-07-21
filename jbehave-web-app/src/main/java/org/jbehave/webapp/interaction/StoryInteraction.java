package org.jbehave.webapp.interaction;

import org.springframework.stereotype.Component;

/**
 * Holds context variables that are needed in the entire JBehave Story.
 * <p/>
 * An Interaction is a spring bean which keeps ThreadLocal state information of a single specification execution available across different steps. The
 * Interaction is setup and torn down before and after every specification execution.
 *
 * @author Daniel Keiss
 */
@Component
public class StoryInteraction extends AbstractInteraction<StoryInteraction> {

    private AbstractInteraction scenarioInteraction;

    /**
     * Store some data in the interaction context for later use.
     */
    public void remember(String key, Object value) {
        super.remember(key, value);
        if (scenarioInteraction != null) {
            scenarioInteraction.remember(key, value);
        }
    }

    /**
     * Store an object for an specific entity in the interaction context for later use. Recall this object with recallObject().
     */
    public void rememberObject(String entityKey, String objectKey, Object value) {
        super.rememberObject(entityKey, objectKey, value);
        if (scenarioInteraction != null) {
            scenarioInteraction.rememberObject(entityKey, objectKey, value);
        }
    }

    public void setSequenceInteraction(AbstractInteraction sequenceInteraction) {
        this.scenarioInteraction = sequenceInteraction;
    }

}
