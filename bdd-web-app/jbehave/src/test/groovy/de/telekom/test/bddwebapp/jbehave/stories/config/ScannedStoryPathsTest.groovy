package de.telekom.test.bddwebapp.jbehave.stories.config

import de.telekom.test.bddwebapp.jbehave.stories.config.ScannedStoryPaths
import org.jbehave.core.configuration.Configuration
import org.jbehave.core.configuration.MostUsefulConfiguration
import spock.lang.Specification

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
class ScannedStoryPathsTest extends Specification {

    def scannedStoryPaths = new ScannedStoryPaths() {
        Configuration configuration() {
            return new MostUsefulConfiguration()
        }

        String storiesBasePath() {
            return this.class.getPackage().getName()
        }
    }

    def "test scanned story paths"() {
        when:
        def storyPaths = scannedStoryPaths.scannedStoryPaths()
        then:
        storyPaths == ["de/telekom/test/bddwebapp/stories/config/scannedstorypathstest/simple_story.story",
                       "de/telekom/test/bddwebapp/stories/config/scannedstorypathstest/test_level01_story.story",
                       "de/telekom/test/bddwebapp/stories/config/scannedstorypathstest/test_level0_story.story",
                       "de/telekom/test/bddwebapp/stories/config/scannedstorypathstest/test_level1_story.story"]
    }

    def "test test level 0 story paths"() {
        when:
        def storyPaths = scannedStoryPaths.testLevelStoryPaths(0)
        then:
        storyPaths == ["de/telekom/test/bddwebapp/stories/config/scannedstorypathstest/simple_story.story",
                       "de/telekom/test/bddwebapp/stories/config/scannedstorypathstest/test_level01_story.story",
                       "de/telekom/test/bddwebapp/stories/config/scannedstorypathstest/test_level0_story.story"]
    }

    def "test test level 1 story paths"() {
        when:
        def storyPaths = scannedStoryPaths.testLevelStoryPaths(1)
        then:
        storyPaths == ["de/telekom/test/bddwebapp/stories/config/scannedstorypathstest/test_level01_story.story",
                       "de/telekom/test/bddwebapp/stories/config/scannedstorypathstest/test_level1_story.story"]
    }

}