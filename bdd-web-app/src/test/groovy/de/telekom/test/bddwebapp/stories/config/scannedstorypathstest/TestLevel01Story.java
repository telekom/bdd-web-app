package de.telekom.test.bddwebapp.stories.config.scannedstorypathstest;

import de.telekom.test.bddwebapp.stories.AbstractStory;
import de.telekom.test.bddwebapp.stories.config.TestLevel;
import org.springframework.context.ApplicationContext;

@TestLevel(testLevels = {0, 1})
public class TestLevel01Story extends AbstractStory {

    public ApplicationContext getApplicationContext() {
        return null;
    }

}