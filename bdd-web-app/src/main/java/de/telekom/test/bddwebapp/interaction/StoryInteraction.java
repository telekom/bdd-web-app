package de.telekom.test.bddwebapp.interaction;

import lombok.Setter;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Holds context variables that are needed in the entire JBehave Story.
 * <p>
 * An Interaction is a spring bean which keeps ThreadLocal state information of a single specification execution available across different steps. The
 * Interaction is setup and torn down before and after every specification execution.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Configuration
@Component
@Scope(scopeName = "prototype")
public class StoryInteraction extends FlatInteraction {

    @Setter
    private ScenarioInteraction scenarioInteraction;

    /**
     * Store some data from scenario interaction to the story interaction context for later use.
     *
     * @param key interaction key
     */
    public void rememberFromScenarioInteraction(String key) {
        super.remember(key, scenarioInteraction.recallNotNull(key));
    }

    /**
     * Store an object from scenario interaction for an specific entity in the story interaction context for later use. Recall this object with recallObject().
     *
     * @param entityKey interaction key - entity part
     * @param objectKey interaction key - object part
     */
    public void rememberObjectFromScenarioInteraction(String entityKey, String objectKey) {
        super.rememberObject(entityKey, objectKey, scenarioInteraction.recallObjectNotNull(entityKey, objectKey));
    }

    @Bean(destroyMethod = "destroy")
    public ThreadLocalTargetSource threadLocalStoryInteraction() {
        var result = new ThreadLocalTargetSource();
        result.setTargetBeanName("storyInteraction");
        return result;
    }


    @Primary
    @Bean(name = "proxiedThreadLocalTargetSourceStoryInteraction")
    public ProxyFactoryBean proxiedThreadLocalTargetSourceStoryInteraction(@Qualifier("threadLocalStoryInteraction") ThreadLocalTargetSource threadLocalStoryInteraction) {
        var result = new ProxyFactoryBean();
        result.setProxyTargetClass(true);
        result.setTargetSource(threadLocalStoryInteraction);
        return result;
    }


}
