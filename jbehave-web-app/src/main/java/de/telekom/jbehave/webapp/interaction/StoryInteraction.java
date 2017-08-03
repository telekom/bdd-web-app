package de.telekom.jbehave.webapp.interaction;

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
     * Store some data in the story interaction context for later use.
     */
    public void remember(String key, Object value) {
        super.remember(key, value);
    }

    /**
     * Store an object for an specific entity in the story interaction context for later use. Recall this object with recallObject().
     */
    public void rememberObject(String entityKey, String objectKey, Object value) {
        super.rememberObject(entityKey, objectKey, value);
    }

    /**
     * Store some data from scenario interaction to the story interaction context for later use.
     */
    public void rememberFromScenarioInteraction(String key) {
        super.remember(key, scenarioInteraction.recallNotNull(key));
    }

    /**
     * Store an object from scenario interaction for an specific entity in the story interaction context for later use. Recall this object with recallObject().
     */
    public void rememberObjectFromScenarioInteraction(String entityKey, String objectKey) {
        super.rememberObject(entityKey, objectKey, scenarioInteraction.recallObjectNotNull(entityKey, objectKey));
    }

    public void setSequenceInteraction(AbstractInteraction sequenceInteraction) {
        this.scenarioInteraction = sequenceInteraction;
    }

}
